<template>
  <section id="features" class="border-y border-brand-border bg-white">
    <div class="container-page grid gap-6 py-7 md:grid-cols-2 lg:grid-cols-4">
      <article
        v-for="feature in features"
        :key="feature.key"
        class="flex items-center gap-5 border-brand-border lg:border-r lg:pr-8 last:border-r-0"
      >
        <div class="flex h-16 w-16 shrink-0 items-center justify-center rounded-full bg-blue-50 text-3xl text-brand-blue">
          {{ feature.icon }}
        </div>
        <div>
          <h3 class="text-base font-bold text-brand-ink">{{ $t(`feature_grid.${feature.key}.title`) }}</h3>
          <p class="mt-2 text-sm leading-6 text-slate-600">{{ $t(`feature_grid.${feature.key}.copy`) }}</p>
        </div>
      </article>
    </div>
  </section>
</template>

<script setup lang="ts">
const features = [
  {
    key: 'countries',
    icon: '◎'
  },
  {
    key: 'realistic',
    icon: '◈'
  },
  {
    key: 'fast',
    icon: '▾'
  },
  {
    key: 'privacy',
    icon: '▣'
  }
]
</script>
