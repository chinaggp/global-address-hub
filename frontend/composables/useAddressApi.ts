import type { AddressResult, CountryOption, RegionOption } from '~/types/address'

export function useAddressApi() {
  const { locale } = useI18n()
  const { getCountryList, getRegionList, countryData } = useCountryService()
  const { generate } = useAddressGenerator()

  const errorMessageMap: Record<string, string> = {
    'Unsupported country code': 'The selected country is not supported yet.',
    'Unsupported region:': 'The selected region is not available for this country.',
    'Region not found': 'The selected region is not available.',
  }

  function resolveError(error: unknown): string {
    if (error instanceof Error) {
      return error.message
    }
    return 'Unable to generate address. Please try again.'
  }

  function wrapError(code: string, fallback: string): string {
    for (const [key, message] of Object.entries(errorMessageMap)) {
      if (code.includes(key)) {
        return message
      }
    }
    return fallback
  }

  async function getCountries(): Promise<CountryOption[]> {
    try {
      return getCountryList()
    } catch {
      return []
    }
  }

  async function getRegions(country: string): Promise<RegionOption[]> {
    try {
      return getRegionList(country)
    } catch {
      return []
    }
  }

  async function getRandomAddress(country: string, region?: string): Promise<AddressResult> {
    try {
      const data = countryData(country)
      return generate(data, region || undefined)
    } catch (error) {
      const raw = resolveError(error)
      throw new Error(wrapError(raw, raw))
    }
  }

  void locale

  return {
    getCountries,
    getRegions,
    getRandomAddress,
  }
}
