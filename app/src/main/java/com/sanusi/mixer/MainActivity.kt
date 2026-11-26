
package com.sanusi.mixer

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.*

class MainActivity : Activity() {

    private val green = Color.rgb(0, 255, 110)
    private val dark = Color.rgb(15, 17, 18)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = dark
        window.navigationBarColor = dark
        window.decorView.systemUiVisibility = 0

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(20, 20, 20, 20)
            setBackgroundColor(dark)
        }

        val scroll = ScrollView(this)
        val content = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
        }

        val title = TextView(this).apply {
            text = "MIXER by Sanusi"
            textSize = 27f
            setTextColor(green)
            gravity = Gravity.CENTER
            setPadding(0, 15, 0, 8)
        }
        content.addView(title)

        addLabel(content, "PROFESSIONAL AUDIO EQUALIZER")

        val powerButton = Button(this).apply {
    text = "POWER ON"
    textSize = 16f
    setTextColor(green)
    setBackgroundColor(Color.rgb(35, 42, 38))

    setOnClickListener {
        eqPower = !eqPower
        audioEngine.setPower(eqPower)
        text = if (eqPower) "POWER ON" else "POWER OFF"
        setTextColor(
            if (eqPower) green else Color.GRAY
        )
    }
}

content.addView(powerButton)
        addLabel(content, "10 BAND EQUALIZER")

        val bands = listOf(
            "31 Hz", "62 Hz", "125 Hz", "250 Hz",
            "500 Hz", "1 kHz", "2 kHz", "4 kHz",
            "8 kHz", "16 kHz"
        )

        for (band in bands) {
            val row = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
            }

            val label = TextView(this).apply {
                text = "$band     0 dB"
                textSize = 14f
                setTextColor(Color.WHITE)
            }

            val seek = SeekBar(this).apply {
                max = 24
                progress = 12
                progressTintList =
                    android.content.res.ColorStateList.valueOf(green)
                thumbTintList =
                    android.content.res.ColorStateList.valueOf(green)
            }

            seek.setOnSeekBarChangeListener(
                object : SeekBar.OnSeekBarChangeListener {
                    override fun onProgressChanged(
if (user && eqPower) {
    val frequencies = intArrayOf(
        31, 62, 125, 250, 500,
        1000, 2000, 4000, 8000, 16000
    )

    val index = bands.indexOf(band)

    if (index >= 0) {
        audioEngine.setBandAtFrequency(
            frequencies[index],
            (value - 12) / 2
        )
    }
}

                        bar: SeekBar?,
                        value: Int,
                        user: Boolean
                    ) {
                        label.text =
                            "$band     ${(value - 12) / 2} dB"
                    }
                    override fun onStartTrackingTouch(bar: SeekBar?) {}
                    override fun onStopTrackingTouch(bar: SeekBar?) {}
                }
            )

            row.addView(label)
            row.addView(seek)
            content.addView(row)
        }

        addLabel(content, "SOUND EFFECTS")

        addLabel(content, "BASS BOOST")

val bassSlider = SeekBar(this).apply {
    max = 100
    progress = 0
}

bassSlider.setOnSeekBarChangeListener(
    object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(
            bar: SeekBar?,
            value: Int,
            user: Boolean
        ) {
            if (user && eqPower) {
                audioEngine.setBass(value)
            }
        }

        override fun onStartTrackingTouch(bar: SeekBar?) {}
        override fun onStopTrackingTouch(bar: SeekBar?) {}
    }
)

content.addView(bassSlider)
        addButton(content, "TREBLE")
        
addLabel(content, "VIRTUALIZER")

val virtualSlider = SeekBar(this).apply {
    max = 100
    progress = 0
}

virtualSlider.setOnSeekBarChangeListener(
    object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(
            bar: SeekBar?,
            value: Int,
            user: Boolean
        ) {
            if (user && eqPower) {
                audioEngine.setVirtualizer(value)
            }
        }

        override fun onStartTrackingTouch(bar: SeekBar?) {}
        override fun onStopTrackingTouch(bar: SeekBar?) {}
    }
)

content.addView(virtualSlider)

addLabel(content, "REVERB")

val reverbSwitch = Switch(this).apply {
    text = "Aktifkan Reverb"
    setTextColor(Color.WHITE)
    setOnCheckedChangeListener { _, checked ->
        if (eqPower) {
            audioEngine.setReverb(checked)
        }
    }
}

content.addView(reverbSwitch)


        addLabel(content, "SOUND PRESETS")

        
addPresetButton(content, "NORMAL",
    intArrayOf(0,0,0,0,0,0,0,0,0,0))

addPresetButton(content, "POP",
    intArrayOf(2,4,5,2,-1,-1,1,3,4,3))

addPresetButton(content, "ROCK",
    intArrayOf(4,3,-2,-3,1,3,5,5,4,4))

addPresetButton(content, "JAZZ",
    intArrayOf(3,2,1,2,-2,-2,0,2,3,4))

addPresetButton(content, "CLASSICAL",
    intArrayOf(4,3,2,1,-1,-1,0,2,3,4))

addPresetButton(content, "VOCAL",
    intArrayOf(-2,-1,0,2,4,4,3,2,1,0))

addPresetButton(content, "DANGDUT",
    intArrayOf(5,5,3,1,-1,1,3,4,4,3))


        
val resetButton = Button(this).apply {
    text = "RESET EQUALIZER"
    textSize = 15f
    setTextColor(green)
    setBackgroundColor(Color.rgb(35, 42, 38))

    setOnClickListener {
        if (eqPower) {
            val frequencies = intArrayOf(
                31, 62, 125, 250, 500,
                1000, 2000, 4000, 8000, 16000
            )

            for (frequency in frequencies) {
                audioEngine.setBandAtFrequency(
                    frequency, 0
                )
            }

            audioEngine.setBass(0)
            audioEngine.setVirtualizer(0)
            audioEngine.setReverb(false)

            Toast.makeText(
                this@MainActivity,
                "Equalizer berhasil direset",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

private fun saveEQSettings(
    key: String,
    value: Int
) {
    getSharedPreferences(
        "SanusiMixerSettings",
        MODE_PRIVATE
    ).edit()
        .putInt(key, value)
        .apply()
}

private fun loadEQSettings(
    key: String,
    defaultValue: Int = 0
): Int {
    return getSharedPreferences(
        "SanusiMixerSettings",
        MODE_PRIVATE
    ).getInt(key, defaultValue)
}
}

content.addView(resetButton)


        scroll.addView(content)
        root.addView(
            scroll,
            LinearLayout.LayoutParams(
                -1, 0, 1f
            )
        )

        setContentView(root)
    }

    private fun addLabel(
        parent: LinearLayout,
        text: String
    ) {
        val label = TextView(this).apply {
            this.text = text
            textSize = 16f
            setTextColor(green)
            setPadding(0, 22, 0, 10)
        }
        parent.addView(label)
    }

    private fun addButton(
        parent: LinearLayout,
        text: String
    ) {
        val button = Button(this).apply {
            this.text = text
            textSize = 14f
            setTextColor(green)
            setBackgroundColor(Color.rgb(35, 42, 38))
            isAllCaps = false
        }
        val params = LinearLayout.LayoutParams(
            -1, 55
        )
        params.setMargins(0, 6, 0, 6)
        parent.addView(button, params)
    }

private fun addPresetButton(
    parent: LinearLayout,
    name: String,
    values: IntArray
) {
    val button = Button(this).apply {
        text = name
        textSize = 14f
        setTextColor(green)
        setBackgroundColor(Color.rgb(35, 42, 38))

        setOnClickListener {
            if (eqPower) {
                val frequencies = intArrayOf(
                    31, 62, 125, 250, 500,
                    1000, 2000, 4000, 8000, 16000
                )

                for (i in frequencies.indices) {
                    audioEngine.setBandAtFrequency(
                        frequencies[i],
                        values[i]
                    )
                }
            }
        }
    }

    parent.addView(button)
}
}

private lateinit var audioEngine: AudioEngine
private var eqPower = true

