import type { AddressResult, CityAddressData, CountryAddressData, RegionAddressData } from '~/types/address'

function number(min: number, max: number): number {
  return Math.floor(Math.random() * (max - min + 1)) + min
}

function pick<T>(list: T[]): T {
  return list[Math.floor(Math.random() * list.length)]
}

function defaultStreet(data: CountryAddressData, _city: CityAddressData, _region: RegionAddressData): string {
  return number(10, 9999) + ' ' + pick(data.streetNames)
}

function defaultPhone(region: RegionAddressData): string {
  const prefix = pick(region.phonePrefixes)
  return prefix + ' ' + number(100, 999) + '-' + number(1000, 9999)
}

interface AddressRule {
  street?: (data: CountryAddressData, city: CityAddressData, region: RegionAddressData) => string
  phone: (region: RegionAddressData) => string
}

const addressRules: Record<string, AddressRule> = {
  US: {
    phone: (region) => {
      const areaCode = pick(region.phonePrefixes)
      return '(' + areaCode + ') ' + number(200, 999) + '-' + number(1000, 9999)
    },
  },
  JP: {
    street: (_data, city) => {
      return city.name + ' ' + number(1, 9) + '-' + number(1, 25) + '-' + number(1, 40)
    },
    phone: (region) => {
      const prefix = pick(region.phonePrefixes)
      return prefix + '-' + number(1000, 9999) + '-' + number(1000, 9999)
    },
  },
  UK: {
    phone: defaultPhone,
  },
  CA: {
    phone: (region) => {
      const areaCode = pick(region.phonePrefixes)
      return '+1 (' + areaCode + ') ' + number(200, 999) + '-' + number(1000, 9999)
    },
  },
  AU: {
    phone: (region) => {
      return '+61 ' + pick(region.phonePrefixes) + ' ' + number(1000, 9999) + ' ' + number(1000, 9999)
    },
  },
  TR: {
    phone: (region) => {
      return '+90 ' + pick(region.phonePrefixes) + ' ' + number(100, 999) + ' ' + number(1000, 9999)
    },
  },
  NG: {
    phone: (region) => {
      return '+234 ' + pick(region.phonePrefixes) + ' ' + number(100, 999) + ' ' + number(1000, 9999)
    },
  },
}

export function useAddressGenerator() {
  function toFullAddress(result: AddressResult): string {
    return toCopyLines(result).join('\n')
  }

  function toCopyLines(result: AddressResult): string[] {
    const regionAndPostal = [result.regionName, result.postalCode].filter(Boolean).join(' ')
    const cityLine = [result.city, regionAndPostal].filter(Boolean).join(', ')
    return [
      result.fullName,
      result.street,
      cityLine,
      result.country,
      result.phone,
    ].filter((line): line is string => Boolean(line))
  }

  function generate(data: CountryAddressData, regionCode?: string): AddressResult {
    const region = regionCode
      ? data.regions.find((r) => r.code.toUpperCase() === regionCode.toUpperCase())!
      : pick(data.regions)

    if (!region) {
      throw new Error(`Region not found: ${regionCode}`)
    }

    const city = pick(region.cities)
    const fullName = pick(data.firstNames) + ' ' + pick(data.lastNames)

    const rule = addressRules[data.countryCode]
    const streetFn = rule?.street || defaultStreet
    const street = streetFn(data, city, region)
    const postalCode = pick(city.postalCodes)
    const phone = rule ? rule.phone(region) : defaultPhone(region)

    const result: AddressResult = {
      country: data.country,
      countryCode: data.countryCode,
      fullName,
      street,
      city: city.name,
      regionCode: region.code,
      regionName: region.name,
      postalCode,
      phone,
      fullAddress: '',
    }

    result.fullAddress = toFullAddress(result)
    return result
  }

  return { generate, toFullAddress, toCopyLines }
}
