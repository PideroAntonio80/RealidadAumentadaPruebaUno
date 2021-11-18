package com.example.realidadaumentadafirstproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.google.ar.core.Config
import com.google.ar.core.Session
import com.google.ar.sceneform.ux.ArFragment
import java.util.*

class CustomARFragment : ArFragment() {

    override fun getSessionConfiguration(session: Session?): Config {
        // Configuración de autofocus de la cámara
        val config = Config(session)
        config.focusMode = Config.FocusMode.AUTO
        config.planeFindingMode = Config.PlaneFindingMode.HORIZONTAL
        return config
    }

    override fun getSessionFeatures(): Set<Session.Feature?> {
        return EnumSet.of(Session.Feature.SHARED_CAMERA)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val frameLayout = super.onCreateView(inflater, container, savedInstanceState) as FrameLayout?
        // planeDiscoveryController.show()
        // planeDiscoveryController.setInstructionView(view)
        return frameLayout
    }
}