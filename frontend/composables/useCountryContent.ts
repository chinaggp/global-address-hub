import { countryOptions } from '~/data/country-pages'
import type { CountryCode, CountryOption, CountryPageContent, FaqItem } from '~/types/address'

const COUNTRY_FAQ_COUNT: Record<CountryCode, number> = {
  US: 4,
  JP: 4,
  UK: 4,
  CA: 4,
  AU: 4,
  TR: 4,
  NG: 4
}

const COUNTRY_SEO_PARAGRAPH_COUNT: Record<CountryCode, number> = {
  US: 2,
  JP: 2,
  UK: 2,
  CA: 2,
  AU: 2,
  TR: 2,
  NG: 2
}

export function useCountryContent() {
  const { t } = useI18n()

  const localizedCountryOptions = computed<CountryOption[]>(() =>
    countryOptions.map((country) => ({
      ...country,
      name: t(`countries.${country.code}.name`)
    }))
  )

  function localizedCountryPage(source: CountryPageContent) {
    return computed<CountryPageContent>(() => ({
      ...source,
      name: t(`countries.${source.code}.name`),
      title: t(`countryPages.${source.code}.title`),
      description: t(`countryPages.${source.code}.description`),
      h1: t(`countryPages.${source.code}.h1`),
      heroCopy: t(`countryPages.${source.code}.heroCopy`),
      regionLabel: t(`countryPages.${source.code}.regionLabel`),
      faq: localizedFaq(source.code),
      seoTitle: t(`countryPages.${source.code}.seoTitle`),
      seoBody: localizedSeoBody(source.code)
    }))
  }

  function localizedFaq(code: CountryCode): FaqItem[] {
    return Array.from({ length: COUNTRY_FAQ_COUNT[code] }, (_, index) => ({
      question: t(`countryPages.${code}.faq.${index}.question`),
      answer: t(`countryPages.${code}.faq.${index}.answer`)
    }))
  }

  function localizedSeoBody(code: CountryCode): string[] {
    return Array.from(
      { length: COUNTRY_SEO_PARAGRAPH_COUNT[code] },
      (_, index) => t(`countryPages.${code}.seoBody.${index}`)
    )
  }

  return {
    localizedCountryOptions,
    localizedCountryPage
  }
}
