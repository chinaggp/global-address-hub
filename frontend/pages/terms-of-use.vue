<template>
  <section class="container-page py-14">
    <div class="max-w-3xl">
      <p class="text-sm font-semibold uppercase tracking-wide text-brand-green">
        {{ $t('common.use_only') }}
      </p>
      <h1 class="mt-4 text-4xl font-bold text-brand-ink">{{ $t('legal.terms.title') }}</h1>
      <div class="mt-8 space-y-6 text-base leading-7 text-slate-600">
        <p v-for="paragraph in paragraphs" :key="paragraph">{{ paragraph }}</p>
      </div>
    </div>
  </section>
</template>

<script setup lang="ts">
const config = useRuntimeConfig()
const { t, locale } = useI18n()
const localePath = useLocalePath()
const siteUrl = String(config.public.siteUrl || 'https://addressgeneration.com').replace(/\/$/, '')
const paragraphs = computed(() => [0, 1, 2].map((index) => t(`legal.terms.body.${index}`)))

useHead(() => ({
  title: t('legal.terms.title'),
  htmlAttrs: { lang: locale.value === 'zh' ? 'zh-CN' : 'en' },
  meta: [
    {
      name: 'description',
      content: t('legal.terms.description')
    }
  ],
  link: [{ rel: 'canonical', href: `${siteUrl}${localePath('/terms-of-use')}` }]
}))
</script>
