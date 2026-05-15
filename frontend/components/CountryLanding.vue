<template>
  <div>
    <section class="bg-brand-surface">
      <div class="container-page grid gap-8 py-12 lg:grid-cols-[1.15fr_0.72fr_1.02fr] lg:items-start lg:py-16">
        <div>
          <p class="text-sm font-semibold uppercase tracking-wide text-brand-green">
            {{ $t('common.use_only') }}
          </p>
          <h1 class="mt-4 text-4xl font-bold tracking-normal text-brand-ink lg:text-5xl">{{ page.h1 }}</h1>
          <p class="mt-5 max-w-2xl text-lg leading-8 text-slate-600">{{ page.heroCopy }}</p>
          <div class="mt-7 flex flex-wrap gap-3">
            <NuxtLink
              v-for="country in countryOptions"
              :key="country.code"
              class="btn-secondary"
              :to="localePath(country.slug)"
            >
              {{ country.name }}
            </NuxtLink>
          </div>
        </div>

        <div class="lg:pt-4">
          <AddressGenerator
            ref="generatorRef"
            :default-country="page.code"
            :region-label="page.regionLabel"
            @generated="address = $event"
          />
        </div>
        <div class="lg:pt-4">
          <AddressResultCard :address="address" @refresh="handleRefresh" />
        </div>
      </div>
    </section>

    <SeoContent :title="page.seoTitle" :paragraphs="page.seoBody" />
    <FAQSection :items="page.faq" :title="$t('countryPages.faqTitle', { country: page.name })" />
  </div>
</template>

<script setup lang="ts">
import type { AddressResult, CountryPageContent } from '~/types/address'

const props = defineProps<{
  page: CountryPageContent
}>()

const address = ref<AddressResult | null>(null)
const generatorRef = ref<{ generate: () => Promise<void> } | null>(null)
const config = useRuntimeConfig()
const { locale } = useI18n()
const localePath = useLocalePath()
const { localizedCountryOptions, localizedCountryPage } = useCountryContent()

const page = localizedCountryPage(props.page)
const countryOptions = localizedCountryOptions
const siteUrl = String(config.public.siteUrl || 'https://addressgeneration.com').replace(/\/$/, '')
const canonical = computed(() => `${siteUrl}${localePath(props.page.slug)}`)

const handleRefresh = () => {
  generatorRef.value?.generate()
}

useHead(() => ({
  title: page.value.title,
  meta: [
    { name: 'description', content: page.value.description },
    { property: 'og:title', content: page.value.title },
    { property: 'og:description', content: page.value.description },
    { property: 'og:url', content: canonical.value }
  ],
  htmlAttrs: { lang: locale.value === 'zh' ? 'zh-CN' : 'en' },
  link: [{ rel: 'canonical', href: canonical.value }],
  script: [
    {
      type: 'application/ld+json',
      innerHTML: JSON.stringify({
        '@context': 'https://schema.org',
        '@type': 'FAQPage',
        mainEntity: page.value.faq.map((item) => ({
          '@type': 'Question',
          name: item.question,
          acceptedAnswer: {
            '@type': 'Answer',
            text: item.answer
          }
        }))
      })
    }
  ]
}))
</script>
