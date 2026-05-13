<template>
  <section class="card p-7">
    <div class="flex items-center justify-between border-b border-brand-border pb-5">
      <div class="flex items-center gap-3">
        <h2 class="text-xl font-bold text-brand-ink">{{ $t('result.title') }}</h2>
        <span class="rounded-full bg-green-100 px-3 py-1 text-sm font-semibold text-green-700">
          {{ $t('result.badge_new') }}
        </span>
      </div>
      <button
        class="group/btn flex h-9 w-9 items-center justify-center rounded-full border border-brand-border bg-white text-slate-400 shadow-sm transition-all hover:border-brand-blue hover:text-brand-blue hover:shadow-md active:scale-95"
        type="button"
        :title="$t('result.refresh')"
        @click="$emit('refresh')"
      >
        <span class="text-xl transition-transform duration-500 group-hover/btn:rotate-180">↻</span>
      </button>
    </div>

    <div v-if="activeAddress" class="mt-5 grid gap-4 text-sm">
      <div v-for="item in displayFields" :key="item.label" class="grid grid-cols-[135px_1fr] gap-4">
        <p class="text-slate-500">{{ item.label }}</p>
        <p class="break-words font-semibold text-brand-ink">{{ item.value || '-' }}</p>
      </div>
    </div>

    <div v-else class="mt-5 rounded-lg bg-brand-surface p-4 text-sm leading-6 text-slate-600">
      {{ $t('result.empty') }}
    </div>

    <div class="mt-7 grid gap-3 sm:grid-cols-2">
      <CopyButton :text="copyAllText" :label="$t('result.copy_all')" />
      <CopyButton :text="copyLinesText" :label="$t('result.copy_lines')" variant="success" />
    </div>
  </section>
</template>

<script setup lang="ts">
import type { AddressResult } from '~/types/address'

const props = defineProps<{
  address?: AddressResult | null
  showSample?: boolean
}>()

defineEmits<{
  refresh: []
}>()

const { t } = useI18n()

const sampleAddress = computed<AddressResult>(() => ({
  fullName: t('result.sample.full_name'),
  country: t('result.sample.country'),
  regionName: t('result.sample.region'),
  city: t('result.sample.city'),
  street: t('result.sample.street'),
  postalCode: '90001',
  phone: '+1 213-555-0187',
  fullAddress: t('result.sample.full_address')
}))

const activeAddress = computed(() => props.address || (props.showSample ? sampleAddress.value : null))

const displayFields = computed(() => [
  { label: t('result.fields.full_name'), value: activeAddress.value?.fullName },
  { label: t('result.fields.street'), value: activeAddress.value?.street || activeAddress.value?.fullAddress },
  { label: t('result.fields.city'), value: activeAddress.value?.city },
  { label: t('result.fields.region'), value: activeAddress.value?.regionName || activeAddress.value?.region },
  { label: t('result.fields.postal_code'), value: activeAddress.value?.postalCode },
  { label: t('result.fields.phone'), value: activeAddress.value?.phone },
  { label: t('result.fields.country'), value: activeAddress.value?.country }
])

const copyAllText = computed(() => activeAddress.value?.fullAddress || '')

const copyLinesText = computed(() => {
  return displayFields.value
    .filter((field) => field.value)
    .map((field) => `${field.label}: ${field.value}`)
    .join('\n')
})
</script>
