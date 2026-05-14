import type { AddressResult } from '~/types/address'

export function useCopyFormat() {
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

  return { toFullAddress, toCopyLines }
}
