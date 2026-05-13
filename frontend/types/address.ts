export type CountryCode = 'US' | 'JP' | 'UK' | 'CA' | 'AU' | 'TR' | 'NG'

export interface CountryOption {
  code: CountryCode
  name: string
  slug: string
}

export interface RegionOption {
  code: string
  name: string
}

export interface AddressResult {
  fullName?: string
  country: string
  region?: string
  regionName?: string
  city?: string
  district?: string
  street?: string
  postalCode?: string
  phone?: string
  fullAddress: string
}

export interface FaqItem {
  question: string
  answer: string
}

export interface CountryPageContent {
  code: CountryCode
  name: string
  slug: string
  title: string
  description: string
  h1: string
  heroCopy: string
  regionLabel: string
  faq: FaqItem[]
  seoTitle: string
  seoBody: string[]
}
