<template>
  <div>
    <section class="bg-brand-surface">
      <div class="container-page grid gap-8 py-12 lg:grid-cols-[1.05fr_0.95fr] lg:items-start lg:py-16">
        <div>
          <p class="text-sm font-semibold uppercase tracking-wide text-brand-green">
            For software testing, form testing, and educational use only.
          </p>
          <h1 class="mt-4 text-4xl font-bold tracking-normal text-brand-ink lg:text-5xl">{{ page.h1 }}</h1>
          <p class="mt-5 max-w-2xl text-lg leading-8 text-slate-600">{{ page.heroCopy }}</p>
          <div class="mt-7 flex flex-wrap gap-3">
            <NuxtLink
              v-for="country in countryOptions"
              :key="country.code"
              class="btn-secondary"
              :to="country.slug"
            >
              {{ country.name }}
            </NuxtLink>
          </div>
        </div>

        <div class="space-y-5">
          <AddressGenerator
            :default-country="page.code"
            :region-label="page.regionLabel"
            @generated="address = $event"
          />
          <AddressResultCard :address="address" />
        </div>
      </div>
    </section>

    <SeoContent :title="page.seoTitle" :paragraphs="page.seoBody" />
    <FAQSection :items="page.faq" :title="`${page.name} Address Generator FAQ`" />
  </div>
</template>

<script setup lang="ts">
import { countryOptions } from '~/data/country-pages'
import type { AddressResult, CountryPageContent } from '~/types/address'

const props = defineProps<{
  page: CountryPageContent
}>()

const address = ref<AddressResult | null>(null)
const config = useRuntimeConfig()
const siteUrl = String(config.public.siteUrl || 'https://globaladdresshub.com').replace(/\/$/, '')
const canonical = `${siteUrl}${props.page.slug}`

useHead({
  title: props.page.title,
  meta: [
    { name: 'description', content: props.page.description },
    { property: 'og:title', content: props.page.title },
    { property: 'og:description', content: props.page.description },
    { property: 'og:url', content: canonical }
  ],
  link: [{ rel: 'canonical', href: canonical }],
  script: [
    {
      type: 'application/ld+json',
      innerHTML: JSON.stringify({
        '@context': 'https://schema.org',
        '@type': 'FAQPage',
        mainEntity: props.page.faq.map((item) => ({
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
})
</script>
