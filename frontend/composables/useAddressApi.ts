import type { AddressResult, CountryOption, RegionOption } from '~/types/address'

function normalizeList<T>(payload: unknown, key: string): T[] {
  if (Array.isArray(payload)) {
    return payload as T[]
  }

  if (payload && typeof payload === 'object') {
    const record = payload as Record<string, unknown>
    if (Array.isArray(record[key])) {
      return record[key] as T[]
    }
    if (Array.isArray(record.data)) {
      return record.data as T[]
    }
  }

  return []
}

function errorMessage(status?: number): string {
  if (status === 400) {
    return 'The selected country or region is not supported yet.'
  }
  if (status === 429) {
    return 'Too many requests. Please wait a moment and try again.'
  }
  if (status && status >= 500) {
    return 'The address service is temporarily unavailable.'
  }
  return 'Unable to reach the address service. Please try again later.'
}

function backendMessage(error: unknown): string {
  const payload =
    (error as { data?: unknown }).data
    || (error as { response?: { _data?: unknown; data?: unknown } }).response?._data
    || (error as { response?: { data?: unknown } }).response?.data

  if (payload && typeof payload === 'object') {
    const message = (payload as Record<string, unknown>).message
    if (typeof message === 'string' && message.trim()) {
      return message
    }
  }

  return ''
}

export function useAddressApi() {
  const config = useRuntimeConfig()
  const { locale } = useI18n()
  const apiBaseUrl = String(config.public.apiBaseUrl || 'http://localhost:8080').replace(/\/$/, '')

  async function request<T>(path: string): Promise<T> {
    try {
      return await $fetch<T>(`${apiBaseUrl}${path}`) as T
    } catch (error) {
      const status = (error as { status?: number; response?: { status?: number } }).status
        || (error as { response?: { status?: number } }).response?.status
      throw new Error(backendMessage(error) || errorMessage(status))
    }
  }

  async function getCountries(): Promise<CountryOption[]> {
    const params = new URLSearchParams({ locale: locale.value })
    const payload = await request<unknown>(`/api/countries?${params.toString()}`)
    return normalizeList<CountryOption>(payload, 'countries')
  }

  async function getRegions(country: string): Promise<RegionOption[]> {
    const params = new URLSearchParams({ country, locale: locale.value })
    const payload = await request<unknown>(`/api/regions?${params.toString()}`)
    return normalizeList<RegionOption>(payload, 'regions')
  }

  async function getRandomAddress(country: string, region?: string): Promise<AddressResult> {
    const params = new URLSearchParams({ country })
    if (region) {
      params.set('region', region)
    }
    return await request<AddressResult>(`/api/address/random?${params.toString()}`)
  }

  return {
    getCountries,
    getRegions,
    getRandomAddress
  }
}
