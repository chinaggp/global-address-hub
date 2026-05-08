<template>
  <section class="container-page py-14">
    <div class="max-w-3xl">
      <p class="text-sm font-semibold uppercase tracking-wide text-brand-green">
        For software testing, form testing, and educational use only.
      </p>
      <h1 class="mt-4 text-4xl font-bold text-brand-ink">Contact</h1>
      <div class="mt-8 space-y-6 text-base leading-7 text-slate-600">
        <p>
          For feedback about address formats, field labels, country support, or software testing use cases, contact
          the GlobalAddressHub maintainer.
        </p>
        <p>
          Email: <a class="font-semibold text-brand-blue" href="mailto:contact@globaladdresshub.com">contact@globaladdresshub.com</a>
        </p>
        <p>
          Please include the country page, expected field format, and the testing scenario you are reviewing.
        </p>
      </div>
    </div>
  </section>
</template>

<script setup lang="ts">
const config = useRuntimeConfig()
const siteUrl = String(config.public.siteUrl || 'https://globaladdresshub.com').replace(/\/$/, '')

useHead({
  title: 'Contact',
  meta: [
    {
      name: 'description',
      content:
        'Contact GlobalAddressHub about address sample data for software testing, form testing, and educational use only.'
    }
  ],
  link: [{ rel: 'canonical', href: `${siteUrl}/contact` }]
})
</script>
