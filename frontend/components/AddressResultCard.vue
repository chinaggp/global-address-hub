<template>
  <section class="card p-7">
    <div class="flex items-center justify-between border-b border-brand-border pb-5">
      <div class="flex items-center gap-3">
        <h2 class="text-xl font-bold text-brand-ink">Generated Address</h2>
        <span class="rounded-full bg-green-100 px-3 py-1 text-sm font-semibold text-green-700">New</span>
      </div>
      <button
        class="group/btn flex h-9 w-9 items-center justify-center rounded-full border border-brand-border bg-white text-slate-400 shadow-sm transition-all hover:border-brand-blue hover:text-brand-blue hover:shadow-md active:scale-95"
        type="button"
        title="Refresh Address"
        @click="$emit('refresh')"
      >
        <span class="text-xl transition-transform group-hover/btn:rotate-180 duration-500">↻</span>
      </button>
    </div>

    <div v-if="activeAddress" class="mt-5 grid gap-4 text-sm">
      <div v-for="item in displayFields" :key="item.label" class="grid grid-cols-[135px_1fr] gap-4">
        <p class="text-slate-500">{{ item.label }}</p>
        <p class="break-words font-semibold text-brand-ink">{{ item.value || '-' }}</p>
      </div>
    </div>

    <div v-else class="mt-5 rounded-lg bg-brand-surface p-4 text-sm leading-6 text-slate-600">
      Generate an address sample to review each field and copy the result.
    </div>

    <div class="mt-7 grid gap-3 sm:grid-cols-2">
      <CopyButton :text="copyAllText" label="▣  Copy All" />
      <CopyButton :text="copyLinesText" label="⇧  Copy in Lines" variant="success" />
    </div>
  </section>
</template>

<script setup lang="ts">
import type { AddressResult } from '~/types/address'

const props = defineProps<{
  address?: AddressResult | null
  showSample?: boolean
}>()

const emit = defineEmits<{
  refresh: []
}>()

const sampleAddress: AddressResult = {
  fullName: 'John David Smith',
  country: 'United States',
  regionName: 'California',
  city: 'Los Angeles',
  street: '742 Evergreen Terrace',
  postalCode: '90001',
  phone: '+1 213-555-0187',
  fullAddress: '742 Evergreen Terrace, Los Angeles, California 90001, United States'
}

const activeAddress = computed(() => props.address || (props.showSample ? sampleAddress : null))

const displayFields = computed(() => [
  { label: 'Full Name', value: activeAddress.value?.fullName },
  { label: 'Street Address', value: activeAddress.value?.street || activeAddress.value?.fullAddress },
  { label: 'City', value: activeAddress.value?.city },
  { label: 'State', value: activeAddress.value?.regionName || activeAddress.value?.region },
  { label: 'ZIP Code', value: activeAddress.value?.postalCode },
  { label: 'Phone Number', value: activeAddress.value?.phone },
  { label: 'Country', value: activeAddress.value?.country }
])

const copyAllText = computed(() => activeAddress.value?.fullAddress || '')

const copyLinesText = computed(() => {
  return displayFields.value
    .filter((field) => field.value)
    .map((field) => `${field.label}: ${field.value}`)
    .join('\n')
})
</script>
