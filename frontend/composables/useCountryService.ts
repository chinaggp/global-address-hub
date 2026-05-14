import type { CountryAddressData, CountryCode, CountryOption, RegionAddressData, RegionOption } from '~/types/address'

import usData from '~/data/address/us-address.json'
import jpData from '~/data/address/jp-address.json'
import ukData from '~/data/address/uk-address.json'
import caData from '~/data/address/ca-address.json'
import auData from '~/data/address/au-address.json'
import trData from '~/data/address/tr-address.json'
import ngData from '~/data/address/ng-address.json'

const countryDataMap: Record<string, CountryAddressData> = {
  US: usData as CountryAddressData,
  JP: jpData as CountryAddressData,
  UK: ukData as CountryAddressData,
  CA: caData as CountryAddressData,
  AU: auData as CountryAddressData,
  TR: trData as CountryAddressData,
  NG: ngData as CountryAddressData,
}

const countrySlugMap: Record<string, string> = {
  US: 'us-address-generator',
  JP: 'japan-address-generator',
  UK: 'uk-address-generator',
  CA: 'canada-address-generator',
  AU: 'australia-address-generator',
  TR: 'turkey-address-generator',
  NG: 'nigeria-address-generator',
}

export function useCountryService() {
  function countryData(code: string): CountryAddressData {
    const data = countryDataMap[code.toUpperCase()]
    if (!data) {
      throw new Error(`Unsupported country code: ${code}`)
    }
    return data
  }

  function regionData(data: CountryAddressData, regionCode?: string): RegionAddressData {
    if (!regionCode) {
      const regions = data.regions
      return regions[Math.floor(Math.random() * regions.length)]
    }
    const region = data.regions.find((r) => r.code.toUpperCase() === regionCode.toUpperCase())
    if (!region) {
      throw new Error(`Unsupported region: ${regionCode} for country: ${data.countryCode}`)
    }
    return region
  }

  function getCountryList(): CountryOption[] {
    return Object.entries(countryDataMap).map(([code, data]) => ({
      code: code as CountryCode,
      name: data.country,
      slug: countrySlugMap[code] || '',
    }))
  }

  function getRegionList(countryCode: string): RegionOption[] {
    const data = countryData(countryCode)
    return data.regions.map((r) => ({
      code: r.code,
      name: r.name,
    }))
  }

  return { countryData, regionData, getCountryList, getRegionList }
}
