<template>
  <section class="container-page py-14">
    <div class="max-w-3xl">
      <p class="text-sm font-semibold uppercase tracking-wide text-brand-green">
        For software testing, form testing, and educational use only.
      </p>
      <h1 class="mt-4 text-4xl font-bold text-brand-ink">Terms of Use</h1>
      <div class="mt-8 space-y-6 text-base leading-7 text-slate-600">
        <p>
          GlobalAddressHub is provided as a lightweight tool for software testing, QA, form validation,
          educational use, demo data, database seed data, and educational examples.
        </p>
        <p>
          Generated output must be treated as sample data. Users are responsible for applying this tool only in
          lawful, controlled, and appropriate testing contexts.
        </p>
        <p>
          The service may change, pause, or limit requests to protect availability and keep the tool reliable.
        </p>
      </div>
    </div>
  </section>
</template>

<script setup lang="ts">
const config = useRuntimeConfig()
const siteUrl = String(config.public.siteUrl || 'https://globaladdresshub.com').replace(/\/$/, '')

useHead({
  title: 'Terms of Use',
  meta: [
    {
      name: 'description',
      content:
        'Terms of Use for GlobalAddressHub address sample generation for software testing and educational use only.'
    }
  ],
  link: [{ rel: 'canonical', href: `${siteUrl}/terms-of-use` }]
})
</script>
