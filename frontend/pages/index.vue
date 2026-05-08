<template>
  <div>
    <section class="relative overflow-hidden bg-white">
      <div class="absolute inset-y-0 left-0 hidden w-1/2 opacity-70 lg:block">
        <div class="world-map-pattern h-full w-full"></div>
      </div>
      <div class="container-page relative grid gap-8 py-8 lg:grid-cols-[1.15fr_0.72fr_1.02fr] lg:items-start">
        <div class="pt-1 lg:pt-8">
          <p class="inline-flex rounded-full bg-blue-50 px-5 py-2 text-sm font-semibold text-brand-blue">
            100% Free · No Sign Up · Instant Copy
          </p>
          <h1 class="mt-8 max-w-3xl text-4xl font-extrabold leading-tight tracking-normal text-brand-ink lg:text-5xl">
            {{ $t('welcome') }}
          </h1>
          <p class="mt-5 max-w-2xl text-base leading-7 text-slate-700">
            {{ $t('subtitle') }}
          </p>
          <ul class="mt-7 space-y-4 text-sm font-medium text-brand-ink">
            <li v-for="(benefit, key) in benefits" :key="key" class="flex items-center gap-3">
              <span class="flex h-4 w-4 items-center justify-center rounded-full bg-brand-blue text-[10px] text-white">✓</span>
              {{ $t(`features.${key}`) }}
            </li>
          </ul>
        </div>

        <div class="lg:pt-4">
          <AddressGenerator ref="generatorRef" @generated="address = $event" />
        </div>
        <div class="lg:pt-4">
          <AddressResultCard :address="address" show-sample @refresh="handleRefresh" />
        </div>
      </div>
    </section>

    <FeatureGrid />

    <section class="bg-brand-surface py-4">
      <div class="container-page grid gap-4 lg:grid-cols-[1fr_1.02fr]">
        <article class="card p-6">
          <h2 class="text-lg font-bold text-brand-ink">About Our Address Generator</h2>
          <p class="mt-4 max-w-3xl text-sm leading-6 text-slate-700">
            Our random address generator provides real-looking addresses for software testing, form filling, account
            registration, and more. All addresses are algorithmically generated and do not belong to any real person.
          </p>
          <div class="mt-4 flex flex-wrap gap-3">
            <NuxtLink
              v-for="country in countryOptions.slice(0, 5)"
              :key="country.code"
              class="inline-flex items-center gap-2 rounded-md border border-brand-border bg-white px-3 py-1.5 text-sm text-brand-ink"
              :to="country.slug"
            >
              <span>{{ flagMap[country.code] }}</span>
              {{ country.name }}
            </NuxtLink>
          </div>
          <NuxtLink class="mt-5 inline-flex text-sm font-semibold text-brand-blue" to="/us-address-generator">
            View all countries →
          </NuxtLink>
        </article>

        <article id="faq" class="card p-6">
          <h2 class="text-lg font-bold text-brand-ink">Frequently Asked Questions</h2>
          <div class="mt-4 overflow-hidden rounded-lg border border-brand-border">
            <details v-for="item in faqItems" :key="item.question" class="group border-b border-brand-border last:border-b-0">
              <summary class="flex cursor-pointer list-none items-center justify-between px-4 py-4 text-sm font-bold text-brand-ink">
                {{ item.question }}
                <span class="text-xl font-normal text-slate-500 group-open:rotate-45">+</span>
              </summary>
              <p class="px-4 pb-4 text-sm leading-6 text-slate-600">{{ item.answer }}</p>
            </details>
          </div>
        </article>
      </div>
    </section>
  </div>
</template>

<script setup lang="ts">
import { countryOptions } from '~/data/country-pages'
import type { AddressResult, FaqItem } from '~/types/address'

const address = ref<AddressResult | null>(null)
const generatorRef = ref<any>(null)
const config = useRuntimeConfig()
const siteUrl = String(config.public.siteUrl || 'https://globaladdresshub.com').replace(/\/$/, '')

const handleRefresh = () => {
  console.log('handleRefresh clicked, generatorRef:', generatorRef.value)
  if (generatorRef.value && typeof generatorRef.value.generate === 'function') {
    generatorRef.value.generate()
  } else {
    console.error('generate method not found on generatorRef')
  }
}

const benefits = {
  format: 'Real address format',
  phone: 'Phone number included',
  zip: 'ZIP / Postal code valid',
  copy: 'One-click copy'
}

const flagMap: Record<string, string> = {
  US: '🇺🇸',
  JP: '🇯🇵',
  UK: '🇬🇧',
  CA: '🇨🇦',
  AU: '🇦🇺',
  TR: '🇹🇷',
  NG: '🇳🇬'
}

const faqItems: FaqItem[] = [
  {
    question: 'Can I use these addresses for account registration?',
    answer:
      'Use them for testing and demos only. They are generated samples, not verified addresses for real account ownership.'
  },
  {
    question: 'Are these real addresses?',
    answer: 'No. The output is formatted to look realistic, but it is generated sample data for reference and QA workflows.'
  },
  {
    question: 'Is this tool free to use?',
    answer: 'Yes. The generator is free to use and does not require sign up for the core copy workflow.'
  }
]

useHead({
  title: 'Random Address Generator for Software Testing',
  meta: [
    {
      name: 'description',
      content:
        'Generate random address sample data for software testing, form testing, QA, and educational use only.'
    },
    { property: 'og:title', content: 'Random Address Generator for Software Testing' },
    {
      property: 'og:description',
      content:
        'Generate address sample data for software testing, form testing, QA, and educational use only.'
    },
    { property: 'og:url', content: siteUrl }
  ],
  link: [{ rel: 'canonical', href: siteUrl }],
  script: [
    {
      type: 'application/ld+json',
      innerHTML: JSON.stringify({
        '@context': 'https://schema.org',
        '@type': 'FAQPage',
        mainEntity: faqItems.map((item) => ({
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

<style scoped>
.world-map-pattern {
  background-image:
    radial-gradient(circle at 16% 18%, rgba(37, 99, 235, 0.14) 0 1px, transparent 1px),
    radial-gradient(circle at 58% 35%, rgba(37, 99, 235, 0.11) 0 1px, transparent 1px),
    radial-gradient(circle at 42% 68%, rgba(37, 99, 235, 0.1) 0 1px, transparent 1px);
  background-size: 12px 12px, 10px 10px, 14px 14px;
  mask-image: radial-gradient(ellipse at 45% 45%, #000 0 52%, transparent 72%);
}
</style>
