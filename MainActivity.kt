package com.mrbon.virus

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import java.util.Random

class MainActivity : AppCompatActivity() {

    private lateinit var layoutHorrorScene: ConstraintLayout
    private lateinit var layoutAntivirusScene: ConstraintLayout
    private lateinit var layoutFailedEnding: LinearLayout

    private lateinit var ivRedScreen: ImageView
    private lateinit var viewGlitchOverlay: View
    private lateinit var ivMrBonHead: ImageView
    private lateinit var tvHackingLog: TextView
    private lateinit var layoutAntivirusTrigger: LinearLayout

    private lateinit var tvScanHeader: TextView
    private lateinit var tvCurrentPhase: TextView
    private lateinit var scanProgressBar: ProgressBar
    private lateinit var tvProgressPercent: TextView
    private lateinit var tvSimulatedPath: TextView
    private lateinit var tvScanConsole: TextView
    private lateinit var btnAction: Button

    private lateinit var tvShutdownStatus: TextView
    private lateinit var btnRestartSimulation: Button

    private var jumpscarePlayer: MediaPlayer? = null
    private var bgMusicPlayer: MediaPlayer? = null

    private val handler = Handler(Looper.getMainLooper())
    private val random = Random()

    private var isAntivirusOpened = false
    private var isSimulationEnded = false
    private var isScanning = false

    private var headPulseAnimator: ObjectAnimator? = null
    private var logoPulseAnimator: ObjectAnimator? = null

    private val hackingMessages = listOf(
        "> ACCESSING SYSTEM_ROOT...",
        "> WARNING: FIREWALL DISABLED",
        "> UPLOADING DEVICE_ID TO REMOTE HOST...",
        "> ENCRYPTING VIRTUAL PARTITION...",
        "> MRBON_VIRUS.EXE RUNNING IN KERNEL",
        "> OVERRIDING SECURITY CERTIFICATES...",
        "> SYSTEM INTEGRITY COMPROMISED: 98%",
        "> LOCKING VIRTUAL RECOVERY..."
    )

    private val simulatedFolders = listOf(
        "[DIR] /system/framework/core-oj.jar",
        "[DIR] /system/bin/app_process64",
        "[DIR] /system/lib64/libandroid_runtime.so",
        "[DIR] /vendor/etc/permissions/platform.xml",
        "[DIR] /data/system/packages.xml",
        "[DIR] /system/priv-app/SystemUI/SystemUI.apk",
        "[DIR] /proc/virtual_memory/mrbon_thread",
        "[FILE] /system/xbin/mrbon_payload.sys",
        "[FILE] /cache/recovery/mrbon_hook.bin",
        "[DIR] /system/etc/security/cacerts/"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        enableFullScreen()

        // Disable back button during active simulation
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (isSimulationEnded) {
                    finish()
                }
            }
        })

        bindViews()
        setupListeners()
        startHorrorExperience()
    }

    private fun bindViews() {
        layoutHorrorScene = findViewById(R.id.layoutHorrorScene)
        layoutAntivirusScene = findViewById(R.id.layoutAntivirusScene)
        layoutFailedEnding = findViewById(R.id.layoutFailedEnding)

        ivRedScreen = findViewById(R.id.ivRedScreen)
        viewGlitchOverlay = findViewById(R.id.viewGlitchOverlay)
        ivMrBonHead = findViewById(R.id.ivMrBonHead)
        tvHackingLog = findViewById(R.id.tvHackingLog)
        layoutAntivirusTrigger = findViewById(R.id.layoutAntivirusTrigger)

        tvScanHeader = findViewById(R.id.tvScanHeader)
        tvCurrentPhase = findViewById(R.id.tvCurrentPhase)
        scanProgressBar = findViewById(R.id.scanProgressBar)
        tvProgressPercent = findViewById(R.id.tvProgressPercent)
        tvSimulatedPath = findViewById(R.id.tvSimulatedPath)
        tvScanConsole = findViewById(R.id.tvScanConsole)
        btnAction = findViewById(R.id.btnAction)

        tvShutdownStatus = findViewById(R.id.tvShutdownStatus)
        btnRestartSimulation = findViewById(R.id.btnRestartSimulation)
    }

    private fun setupListeners() {
        layoutAntivirusTrigger.setOnClickListener {
            if (!isAntivirusOpened && !isSimulationEnded) {
                openAntivirusScanner()
            }
        }

        btnAction.setOnClickListener {
            if (isSimulationEnded) {
                finish()
            } else if (!isScanning) {
                startFifteenSecondScan()
            }
        }

        btnRestartSimulation.setOnClickListener {
            recreate()
        }
    }

    private fun startHorrorExperience() {
        // 1. Immediately play 1-second MrBon Virus jumpscare
        playJumpscareSound()

        // 2. Start 1-minute horror & hacking background music right after jumpscare
        handler.postDelayed({
            if (!isSimulationEnded) {
                startBackgroundHorrorMusic()
            }
        }, 1000L)

        // 3. Start smooth animations and glitch effects
        startHorrorAnimations()

        // 4. Hidden 60-second timer for Ending 1 (Failed)
        handler.postDelayed({
            if (!isAntivirusOpened && !isSimulationEnded) {
                triggerEndingOneFailed()
            }
        }, 60000L)
    }

    private fun startHorrorAnimations() {
        // Pulse & shake animation for MrBon head
        val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 0.96f, 1.06f)
        val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 0.96f, 1.06f)
        headPulseAnimator = ObjectAnimator.ofPropertyValuesHolder(ivMrBonHead, scaleX, scaleY).apply {
            duration = 650
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.REVERSE
            interpolator = AccelerateDecelerateInterpolator()
            start()
        }

        // Pulse animation for IronTurboMax trigger
        val btnScaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 0.95f, 1.05f)
        val btnScaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 0.95f, 1.05f)
        logoPulseAnimator = ObjectAnimator.ofPropertyValuesHolder(layoutAntivirusTrigger, btnScaleX, btnScaleY).apply {
            duration = 500
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.REVERSE
            start()
        }

        // Glitch effect loop
        val glitchRunnable = object : Runnable {
            override fun run() {
                if (!isAntivirusOpened && !isSimulationEnded) {
                    val offsetX = (random.nextInt(25) - 12).toFloat()
                    val offsetY = (random.nextInt(17) - 8).toFloat()
                    ivMrBonHead.translationX = offsetX
                    ivMrBonHead.translationY = offsetY

                    viewGlitchOverlay.alpha = if (random.nextInt(10) > 6) 0.35f else 0.0f
                    ivRedScreen.alpha = 0.7f + (random.nextFloat() * 0.3f)

                    handler.postDelayed(this, 120L)
                }
            }
        }
        handler.post(glitchRunnable)

        // Hacking log feed loop
        var msgIndex = 0
        val logRunnable = object : Runnable {
            override fun run() {
                if (!isAntivirusOpened && !isSimulationEnded) {
                    val line1 = hackingMessages[msgIndex % hackingMessages.size]
                    val line2 = hackingMessages[(msgIndex + 1) % hackingMessages.size]
                    val line3 = hackingMessages[(msgIndex + 2) % hackingMessages.size]
                    tvHackingLog.text = "$line1
$line2
$line3"
                    msgIndex++
                    handler.postDelayed(this, 1800L)
                }
            }
        }
        handler.post(logRunnable)
    }

    private fun openAntivirusScanner() {
        isAntivirusOpened = true
        headPulseAnimator?.cancel()
        logoPulseAnimator?.cancel()

        layoutHorrorScene.visibility = View.GONE
        layoutAntivirusScene.visibility = View.VISIBLE

        startFifteenSecondScan()
    }

    private fun startFifteenSecondScan() {
        isScanning = true
        btnAction.visibility = View.GONE
        layoutAntivirusScene.setBackgroundColor(Color.parseColor("#071326"))
        tvScanHeader.text = "IRONTURBOMAX SECURITY SUITE"
        tvScanHeader.setTextColor(Color.parseColor("#4DA6FF"))
        scanProgressBar.progress = 0
        tvProgressPercent.text = "0%"
        tvScanConsole.text = "> Starting IronTurboMax deep system scan...
"

        val phases = listOf(
            "IronTurboMax Initializing...",
            "Searching for threats...",
            "Scanning application files...",
            "Detecting suspicious activity...",
            "MrBon Virus Detected...",
            "Removing Threats..."
        )

        val totalDurationMs = 15000L
        val tickIntervalMs = 150L
        val totalTicks = (totalDurationMs / tickIntervalMs).toInt() // 100 ticks = 15 seconds
        var currentTick = 0
        var lastLoggedPhaseIndex = -1

        val scanRunnable = object : Runnable {
            override fun run() {
                if (currentTick <= totalTicks) {
                    val percent = (currentTick * 100) / totalTicks
                    scanProgressBar.progress = percent
                    tvProgressPercent.text = "$percent%"

                    // Update animated simulated folders & files
                    val folder = simulatedFolders[currentTick % simulatedFolders.size]
                    tvSimulatedPath.text = folder

                    // Determine current phase message across the 15 seconds
                    val phaseIndex = when {
                        percent < 15 -> 0
                        percent < 32 -> 1
                        percent < 50 -> 2
                        percent < 68 -> 3
                        percent < 85 -> 4
                        else -> 5
                    }

                    val currentPhaseText = phases[phaseIndex]
                    tvCurrentPhase.text = currentPhaseText

                    if (phaseIndex != lastLoggedPhaseIndex) {
                        lastLoggedPhaseIndex = phaseIndex
                        tvScanConsole.append("
> $currentPhaseText")
                    }

                    currentTick++
                    handler.postDelayed(this, tickIntervalMs)
                } else {
                    evaluateScanOutcome()
                }
            }
        }
        handler.post(scanRunnable)
    }

    private fun evaluateScanOutcome() {
        isScanning = false
        val roll = random.nextInt(100)

        // 10% chance of Antivirus Failure
        if (roll < 10) {
            layoutAntivirusScene.setBackgroundColor(Color.parseColor("#2A0505"))
            tvScanHeader.text = "IRON TURBOMAX ERROR"
            tvScanHeader.setTextColor(Color.parseColor("#FF4444"))
            tvCurrentPhase.text = "SCAN FAILED"
            tvCurrentPhase.setTextColor(Color.parseColor("#FF4444"))
            tvSimulatedPath.text = "[ERROR] /system/xbin/mrbon_payload.sys [LOCKED]"

            tvScanConsole.setTextColor(Color.parseColor("#FF6B6B"))
            tvScanConsole.text =
                "IRON TURBOMAX ERROR

" +
                "UNABLE TO REMOVE MRBON VIRUS
" +
                "THREAT STILL ACTIVE
" +
                "SCAN FAILED"

            btnAction.text = "RETRY SCAN"
            btnAction.setBackgroundColor(Color.parseColor("#B71C1C"))
            btnAction.visibility = View.VISIBLE
        } else {
            // 90% chance: ENDING 2 — SUCCESS
            isSimulationEnded = true
            stopAllAudio()

            // Switch red effects to green security theme
            layoutAntivirusScene.setBackgroundColor(Color.parseColor("#042611"))
            tvScanHeader.text = "IRONTURBOMAX PROTECTION ACTIVE"
            tvScanHeader.setTextColor(Color.parseColor("#00E676"))
            tvCurrentPhase.text = "DEVICE PROTECTED"
            tvCurrentPhase.setTextColor(Color.parseColor("#00E676"))
            tvSimulatedPath.setBackgroundColor(Color.parseColor("#083B1C"))
            tvSimulatedPath.setTextColor(Color.parseColor("#69F0AE"))
            tvSimulatedPath.text = "[CLEAN] 0 THREATS REMAINING"

            tvScanConsole.setBackgroundColor(Color.parseColor("#02170A"))
            tvScanConsole.setTextColor(Color.parseColor("#00E676"))
            tvScanConsole.text =
                "MRBON VIRUS DETECTED
" +
                "MRBON VIRUS REMOVED
" +
                "ANTIVIRUS SUCCESSFULLY ACTIVATED
" +
                "ALL THREATS REMOVED
" +
                "DEVICE PROTECTED

" +
                "ENDING 2 — SUCCESS"

            btnAction.text = "EXIT SIMULATION"
            btnAction.setBackgroundColor(Color.parseColor("#00C853"))
            btnAction.visibility = View.VISIBLE
        }
    }

    private fun triggerEndingOneFailed() {
        isSimulationEnded = true
        headPulseAnimator?.cancel()
        logoPulseAnimator?.cancel()
        stopAllAudio()

        // Repeat jumpscare + intense red glitch flash before fake shutdown
        playJumpscareSound()
        viewGlitchOverlay.alpha = 0.85f
        ivMrBonHead.scaleX = 1.25f
        ivMrBonHead.scaleY = 1.25f

        handler.postDelayed({
            // Fake device shutdown transition to black screen
            layoutHorrorScene.visibility = View.GONE
            layoutAntivirusScene.visibility = View.GONE
            layoutFailedEnding.visibility = View.VISIBLE

            tvShutdownStatus.text = "Shutting down..."

            handler.postDelayed({
                tvShutdownStatus.text =
                    "SYSTEM SHUTDOWN

" +
                    "MRBON VIRUS HAS TAKEN CONTROL

" +
                    "ENDING 1 — FAILED"
                btnRestartSimulation.visibility = View.VISIBLE
            }, 1500L)
        }, 1100L)
    }

    private fun playJumpscareSound() {
        try {
            jumpscarePlayer?.release()
            jumpscarePlayer = MediaPlayer.create(this, R.raw.jumpscare)
            jumpscarePlayer?.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun startBackgroundHorrorMusic() {
        try {
            bgMusicPlayer?.release()
            bgMusicPlayer = MediaPlayer.create(this, R.raw.horror_bg)
            bgMusicPlayer?.isLooping = true
            bgMusicPlayer?.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun stopAllAudio() {
        try {
            if (jumpscarePlayer?.isPlaying == true) {
                jumpscarePlayer?.stop()
            }
            jumpscarePlayer?.release()
            jumpscarePlayer = null
        } catch (e: Exception) {
            e.printStackTrace()
        }

        try {
            if (bgMusicPlayer?.isPlaying == true) {
                bgMusicPlayer?.stop()
            }
            bgMusicPlayer?.release()
            bgMusicPlayer = null
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun enableFullScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.let { controller ->
                controller.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                controller.systemBarsBehavior =
                    WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopAllAudio()
        headPulseAnimator?.cancel()
        logoPulseAnimator?.cancel()
        handler.removeCallbacksAndMessages(null)
    }
}
