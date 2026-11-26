
package com.sanusi.mixer

import android.media.audiofx.Equalizer
import android.media.audiofx.BassBoost
import android.media.audiofx.Virtualizer
import android.media.audiofx.PresetReverb

class AudioEngine {

    private var equalizer: Equalizer? = null
    private var bassBoost: BassBoost? = null
    private var virtualizer: Virtualizer? = null
    private var reverb: PresetReverb? = null

    init {
        try {
            equalizer = Equalizer(0, 0).apply {
                enabled = true
            }
        } catch (_: Exception) {}

        try {
            bassBoost = BassBoost(0, 0).apply {
                enabled = true
            }
        } catch (_: Exception) {}

        try {
            virtualizer = Virtualizer(0, 0).apply {
                enabled = true
            }
        } catch (_: Exception) {}

        try {
            reverb = PresetReverb(0, 0).apply {
                enabled = true
            }
        } catch (_: Exception) {}
    }

    fun isAvailable(): Boolean {
        return equalizer != null
    }

    fun setBand(index: Int, db: Int) {
        try {
            val eq = equalizer ?: return
            if (index !in 0 until eq.numberOfBands) return

            val range = eq.bandLevelRange
            val level = (db * 100)
                .coerceIn(
                    range[0].toInt(),
                    range[1].toInt()
                )

            eq.setBandLevel(index.toShort(), level.toShort())
        } catch (_: Exception) {}
    }
     
fun setBandAtFrequency(freqHz: Int, db: Int) {
    try {
        val eq = equalizer ?: return
        if (eq.numberOfBands == 0) return

        var nearestBand: Short = 0
        var smallestDifference = Long.MAX_VALUE

        for (i in 0 until eq.numberOfBands) {
            val center = eq.getCenterFreq(i.toShort()) / 1000
            val difference = kotlin.math.abs(
                center.toLong() - freqHz.toLong()
            )

            if (difference < smallestDifference) {
                smallestDifference = difference
                nearestBand = i.toShort()
            }
        }

        val range = eq.bandLevelRange
        val level = (db * 100).coerceIn(
            range[0].toInt(),
            range[1].toInt()
        )

        eq.setBandLevel(nearestBand, level.toShort())

    } catch (_: Exception) {}
}

    fun setBass(value: Int) {
        try {
            bassBoost?.setStrength(
                (value * 10).coerceIn(0, 1000).toShort()
            )
        } catch (_: Exception) {}
    }

    fun setVirtualizer(value: Int) {
        try {
            virtualizer?.setStrength(
                (value * 10).coerceIn(0, 1000).toShort()
            )
        } catch (_: Exception) {}
    }

    fun setReverb(enabled: Boolean) {
        try {
            reverb?.enabled = enabled
        } catch (_: Exception) {}
    }

    fun setPower(enabled: Boolean) {
        try { equalizer?.enabled = enabled } catch (_: Exception) {}
        try { bassBoost?.enabled = enabled } catch (_: Exception) {}
        try { virtualizer?.enabled = enabled } catch (_: Exception) {}
        try { reverb?.enabled = enabled } catch (_: Exception) {}
    }

    fun release() {
        try { equalizer?.release() } catch (_: Exception) {}
        try { bassBoost?.release() } catch (_: Exception) {}
        try { virtualizer?.release() } catch (_: Exception) {}
        try { reverb?.release() } catch (_: Exception) {}

        equalizer = null
        bassBoost = null
        virtualizer = null
        reverb = null
    }
}
