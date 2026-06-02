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
          <li aria-current="page">
            <div class="flex items-center">
              <span class="mx-2 text-slate-400">/</span>
              <span class="text-slate-700 font-medium">{{ $t('blog.title') }}</span>
            </div>
          </li>
        </ol>
      </nav>

      <!-- Blog Header -->
      <div class="max-w-3xl mb-12">
        <p class="text-sm font-semibold uppercase tracking-wide text-brand-green">
          {{ $t('common.use_only') }}
        </p>
        <h1 class="mt-4 text-4xl font-extrabold text-brand-ink lg:text-5xl">
          {{ $t('blog.meta_title') }}
        </h1>
        <p class="mt-4 text-lg text-slate-600 leading-8">
          {{ $t('blog.meta_description') }}
        </p>
      </div>

      <!-- Blog Posts Grid -->
      <div class="grid gap-8 md:grid-cols-2 lg:grid-cols-3">
        <article
          v-for="post in blogPosts"
          :key="post.slug"
          class="card group flex flex-col justify-between overflow-hidden bg-white p-6 transition-all duration-300 hover:-translate-y-1 hover:shadow-lg"
        >
          <div>
            <!-- Tag & Meta -->
            <div class="flex items-center justify-between mb-4">
              <span class="inline-flex items-center rounded-full bg-blue-50 px-2.5 py-0.5 text-xs font-semibold text-brand-blue">
                {{ post.tag }}
              </span>
              <span class="text-xs text-slate-400">{{ post.readTime }}</span>
            </div>

            <!-- Title -->
            <h2 class="text-xl font-bold text-brand-ink mb-3 group-hover:text-brand-blue transition-colors duration-200">
              <NuxtLink :to="localePath(`/blog/${post.slug}`)">
                {{ getPostData(post.slug).title }}
              </NuxtLink>
            </h2>

            <!-- Description -->
            <p class="text-sm text-slate-600 leading-6 mb-6 line-clamp-3">
              {{ getPostData(post.slug).description }}
            </p>
          </div>

          <!-- Bottom Row: Author & Action -->
          <div class="flex items-center justify-between pt-4 border-t border-brand-border">
            <div class="text-xs text-slate-500">
              <span class="font-medium text-slate-700">{{ post.author }}</span>
              <span class="mx-1.5">•</span>
              <span>{{ post.date }}</span>
            </div>
            <NuxtLink
              :to="localePath(`/blog/${post.slug}`)"
              class="text-xs font-bold text-brand-blue group-hover:underline flex items-center gap-0.5"
            >
              <span>{{ $t('blog.read_more') }}</span>
            </NuxtLink>
          </div>
        </article>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { blogPosts } from '~/data/blog-posts'
import { blogContentEn, blogContentZh } from '~/data/blog-content'

const config = useRuntimeConfig()
const { t, locale } = useI18n()
const localePath = useLocalePath()
const siteUrl = String(config.public.siteUrl || 'https://addressgeneration.com').replace(/\/$/, '')

function getPostData(slug: string) {
  const contentMap = locale.value === 'zh' ? blogContentZh : blogContentEn
  return contentMap[slug] || { title: '', description: '' }
}

useHead(() => ({
  title: t('blog.meta_title'),
  htmlAttrs: { lang: locale.value === 'zh' ? 'zh-CN' : 'en' },
  meta: [
    {
      name: 'description',
      content: t('blog.meta_description')
    },
    { property: 'og:title', content: t('blog.meta_title') },
    {
      property: 'og:description',
      content: t('blog.meta_description')
    },
    { property: 'og:url', content: `${siteUrl}${localePath('/blog')}` }
  ],
  link: [{ rel: 'canonical', href: `${siteUrl}${localePath('/blog')}` }]
}))
</script>
