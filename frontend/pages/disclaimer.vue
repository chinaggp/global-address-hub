<template>
  <section class="container-page py-14">
    <div class="max-w-3xl">
      <p class="text-sm font-semibold uppercase tracking-wide text-brand-green">
        For software testing, form testing, and educational use only.
      </p>
      <h1 class="mt-4 text-4xl font-bold text-brand-ink">Disclaimer</h1>
      <div class="mt-8 space-y-6 text-base leading-7 text-slate-600">
        <p>
          GlobalAddressHub generates sample address data to help with software testing, form testing, QA, and
          educational demos.
        </p>
        <p>
          Generated addresses are algorithmic or sample data. They are not verified location records and do not
          represent a real person.
        </p>
        <p>
          Field formats may vary by backend data quality and country rules. Always validate your production forms
          against your own business requirements.
        </p>
      </div>
    </div>
  </section>
</template>

<script setup lang="ts">
const config = useRuntimeConfig()
const siteUrl = String(config.public.siteUrl || 'https://globaladdresshub.com').replace(/\/$/, '')

useHead({
  title: 'Disclaimer',
  meta: [
    {
      name: 'description',
      content:
        'Disclaimer for GlobalAddressHub generated address sample data for software testing and educational use only.'
    }
  ],
  link: [{ rel: 'canonical', href: `${siteUrl}/disclaimer` }]
})
</script>
