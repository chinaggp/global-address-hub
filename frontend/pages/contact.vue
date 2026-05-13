<template>
  <section class="container-page py-14">
    <div class="max-w-3xl">
      <p class="text-sm font-semibold uppercase tracking-wide text-brand-green">
        {{ $t('common.use_only') }}
      </p>
      <h1 class="mt-4 text-4xl font-bold text-brand-ink">{{ $t('legal.contact.title') }}</h1>
      <div class="mt-8 space-y-6 text-base leading-7 text-slate-600">
        <p v-for="paragraph in paragraphs" :key="paragraph">{{ paragraph }}</p>
        <p>
          {{ $t('legal.contact.email_label') }}
          <a class="font-semibold text-brand-blue" href="mailto:contact@globaladdresshub.com">contact@globaladdresshub.com</a>
        </p>
      </div>
    </div>
  </section>
</template>

<script setup lang="ts">
const config = useRuntimeConfig()
const { t, locale } = useI18n()
const localePath = useLocalePath()
const siteUrl = String(config.public.siteUrl || 'https://globaladdresshub.com').replace(/\/$/, '')
const paragraphs = computed(() => [t('legal.contact.body.0'), t('legal.contact.body.1')])

useHead(() => ({
  title: t('legal.contact.title'),
  htmlAttrs: { lang: locale.value === 'zh' ? 'zh-CN' : 'en' },
  meta: [
    {
      name: 'description',
      content: t('legal.contact.description')
    }
  ],
  link: [{ rel: 'canonical', href: `${siteUrl}${localePath('/contact')}` }]
}))
</script>
