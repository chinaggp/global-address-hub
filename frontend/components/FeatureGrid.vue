<template>
  <section id="features" class="border-y border-brand-border bg-white">
    <div class="container-page grid gap-6 py-7 md:grid-cols-2 lg:grid-cols-4">
      <article
        v-for="feature in features"
        :key="feature.title"
        class="flex items-center gap-5 border-brand-border lg:border-r lg:pr-8 last:border-r-0"
      >
        <div class="flex h-16 w-16 shrink-0 items-center justify-center rounded-full bg-blue-50 text-3xl text-brand-blue">
          {{ feature.icon }}
        </div>
        <div>
          <h3 class="text-base font-bold text-brand-ink">{{ feature.title }}</h3>
          <p class="mt-2 text-sm leading-6 text-slate-600">{{ feature.copy }}</p>
        </div>
      </article>
    </div>
  </section>
</template>

<script setup lang="ts">
const features = [
  {
    icon: '◎',
    title: 'Multiple Countries',
    copy: 'Support for 20+ countries and regions.'
  },
  {
    icon: '◈',
    title: 'Realistic & Valid',
    copy: 'Addresses follow local formatting rules.'
  },
  {
    icon: '▾',
    title: 'Fast & Free',
    copy: 'Instantly generate and copy with one click.'
  },
  {
    icon: '▣',
    title: 'Privacy First',
    copy: 'No personal data collected. 100% free to use.'
  }
]
</script>
