<template>
  <div class="bg-brand-surface min-h-screen py-12">
    <div class="container-page">
      <!-- Breadcrumbs -->
      <nav class="flex mb-8 text-sm text-slate-500" aria-label="Breadcrumb">
        <ol class="inline-flex items-center space-x-1 md:space-x-3">
          <li class="inline-flex items-center">
            <NuxtLink :to="localePath('/')" class="inline-flex items-center hover:text-brand-blue transition">
              {{ $t('nav.home') }}
            </NuxtLink>
          </li>
          <li>
            <div class="flex items-center">
              <span class="mx-2 text-slate-400">/</span>
              <NuxtLink :to="localePath('/blog')" class="hover:text-brand-blue transition">
                {{ $t('blog.title') }}
              </NuxtLink>
            </div>
          </li>
          <li aria-current="page">
            <div class="flex items-center">
              <span class="mx-2 text-slate-400">/</span>
              <span class="text-slate-700 font-medium line-clamp-1 max-w-[200px] sm:max-w-md">
                {{ postTitle }}
              </span>
            </div>
          </li>
        </ol>
      </nav>

      <!-- Layout Grid (Main Content & Sidebar) -->
      <div class="grid gap-8 lg:grid-cols-[1fr_320px]">
        <!-- Main Article Container -->
        <article class="card bg-white p-6 sm:p-10">
          <!-- Article Header -->
          <header class="border-b border-brand-border pb-8">
            <div class="flex flex-wrap items-center gap-3 text-sm text-slate-500 mb-4">
              <span class="inline-flex items-center rounded-full bg-blue-50 px-2.5 py-0.5 text-xs font-semibold text-brand-blue">
                {{ metadata.tag }}
              </span>
              <span>•</span>
              <span>{{ metadata.readTime }}</span>
              <span>•</span>
              <time :datetime="metadata.date">{{ metadata.date }}</time>
            </div>
            <h1 class="text-3xl font-extrabold text-brand-ink sm:text-4xl leading-tight">
              {{ postTitle }}
            </h1>
            <div class="mt-6 flex items-center gap-3">
              <div class="flex h-10 w-10 items-center justify-center rounded-full bg-brand-blue text-sm font-bold text-white uppercase">
                {{ metadata.author.charAt(0) }}
              </div>
              <div>
                <p class="text-sm font-bold text-brand-ink">{{ metadata.author }}</p>
                <p class="text-xs text-slate-500">Technical Writer / QA Specialist</p>
              </div>
            </div>
          </header>

          <!-- Article Body -->
          <div class="prose max-w-none mt-8">
            <template v-for="(block, idx) in postContent" :key="idx">
              <!-- Paragraph -->
              <p
                v-if="block.type === 'p'"
                class="text-base text-slate-700 leading-8 mb-6 whitespace-pre-line"
              >
                {{ block.text }}
              </p>
              <!-- Heading 2 -->
              <h2
                v-else-if="block.type === 'h2'"
                class="text-2xl font-bold text-brand-ink mt-10 mb-4 pb-2 border-b border-brand-border"
              >
                {{ block.text }}
              </h2>
              <!-- Heading 3 -->
              <h3
                v-else-if="block.type === 'h3'"
                class="text-xl font-bold text-brand-ink mt-8 mb-3"
              >
                {{ block.text }}
              </h3>
              <!-- Unordered List -->
              <ul
                v-else-if="block.type === 'ul'"
                class="list-disc pl-6 space-y-2 mb-6 text-slate-700 leading-7"
              >
                <li v-for="item in block.items" :key="item">{{ item }}</li>
              </ul>
            </template>
          </div>
        </article>

        <!-- Sidebar (Related Links & Generators) -->
        <aside class="space-y-6">
          <!-- Address Generator Links -->
          <div class="card bg-white p-6">
            <h2 class="text-base font-bold text-brand-ink mb-4 pb-2 border-b border-brand-border">
              {{ $t('footer.popular_countries') }}
            </h2>
            <div class="flex flex-col gap-2.5">
              <NuxtLink
                v-for="country in countryOptions"
                :key="country.code"
                :to="localePath(country.slug)"
                class="flex items-center justify-between rounded-md border border-brand-border bg-brand-surface p-3 text-sm font-semibold text-brand-ink transition hover:border-brand-blue hover:text-brand-blue"
              >
                <div class="flex items-center gap-2">
                  <span>{{ flagMap[country.code] }}</span>
                  <span>{{ country.name }}</span>
                </div>
                <span class="text-xs font-normal text-slate-400">Go →</span>
              </NuxtLink>
            </div>
          </div>

          <!-- QA Notice -->
          <div class="card bg-slate-900 p-6 text-white">
            <div class="flex h-10 w-10 items-center justify-center rounded-full bg-brand-blue text-lg text-white mb-4">
              ✓
            </div>
            <h3 class="text-sm font-bold text-white mb-2">100% Free QA Tool</h3>
            <p class="text-xs text-slate-400 leading-5">
              {{ $t('common.use_only') }} Use the randomly generated data responsibly for testing and demonstration workflows.
            </p>
          </div>
        </aside>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { blogPosts } from '~/data/blog-posts'
import type { CountryCode } from '~/types/address'

const route = useRoute()
const config = useRuntimeConfig()
const { t, locale } = useI18n()
const localePath = useLocalePath()
const { localizedCountryOptions } = useCountryContent()

const slug = String(route.params.slug)
const metadata = blogPosts.find((p) => p.slug === slug)

if (!metadata) {
  throw showError({ statusCode: 404, statusMessage: 'Post Not Found' })
}

const countryOptions = localizedCountryOptions
const flagMap: Record<CountryCode, string> = {
  US: '🇺🇸',
  JP: '🇯🇵',
  UK: '🇬🇧',
  CA: '🇨🇦',
  AU: '🇦🇺',
  TR: '🇹🇷',
  NG: '🇳🇬'
}

import { blogContentEn, blogContentZh } from '~/data/blog-content'

const postData = computed(() => {
  const contentMap = locale.value === 'zh' ? blogContentZh : blogContentEn
  return contentMap[slug]
})

const postTitle = computed(() => postData.value?.title || '')
const postDescription = computed(() => postData.value?.description || '')
const postContent = computed(() => postData.value?.content || [])

const siteUrl = String(config.public.siteUrl || 'https://addressgeneration.com').replace(/\/$/, '')
const canonical = computed(() => `${siteUrl}${localePath(`/blog/${slug}`)}`)

useHead(() => ({
  title: postTitle.value,
  htmlAttrs: { lang: locale.value === 'zh' ? 'zh-CN' : 'en' },
  meta: [
    { name: 'description', content: postDescription.value },
    { property: 'og:title', content: postTitle.value },
    { property: 'og:description', content: postDescription.value },
    { property: 'og:url', content: canonical.value }
  ],
  link: [{ rel: 'canonical', href: canonical.value }]
}))
</script>
