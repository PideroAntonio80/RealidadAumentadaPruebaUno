package com.example.realidadaumentadafirstproject

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.ar.core.Anchor
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.assets.RenderableSource
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode

class MainActivity : AppCompatActivity() {

    private var renderable: ModelRenderable? = null
    private lateinit var arFragment: ArFragment
    //private lateinit var arFragment: CustomARFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        arFragment = supportFragmentManager.findFragmentById(R.id.ux_fragment) as ArFragment
        //arFragment = supportFragmentManager.findFragmentById(R.id.ux_fragment) as CustomARFragment

        //downloadModel("https://firebasestorage.googleapis.com/v0/b/realidadaumentadafirstproject.appspot.com/o/imagesar%2FBabyElephant_GLB.glb?alt=media&token=b048d4a8-5714-4462-93f6-f03728c6f221")
        downloadModel("https://firebasestorage.googleapis.com/v0/b/realidadaumentadafirstproject.appspot.com/o/imagesar%2Fa0ff1c60-5e21-49e5-946a-675515d98736.glb?alt=media&token=32b21900-58a0-489f-b4e8-e8536d97f493")

        arFragment.setOnTapArPlaneListener { hitResult, plane, motionEvent ->
            if (renderable == null) { return@setOnTapArPlaneListener }
            val anchor: Anchor = hitResult.createAnchor()
            val anchorNode = AnchorNode(anchor)
            anchorNode.setParent(arFragment.arSceneView.scene)
            val node = TransformableNode(arFragment.transformationSystem)
            node.renderable = renderable
            node.scaleController.minScale = 0.06f
            node.scaleController.maxScale = 1.0f
            node.worldScale = Vector3(0.5f, 0.5f, 0.5f)
            node.setParent(anchorNode)
            node.select()
        }
    }

    private fun downloadModel(URL_ruta: String?) {
        val renderableSource = RenderableSource.builder()
            .setSource(this, Uri.parse(URL_ruta), RenderableSource.SourceType.GLB)
            .setRecenterMode(RenderableSource.RecenterMode.CENTER)
            .build()

        ModelRenderable.Builder()
            .setSource(this, renderableSource)
            .build()
            .thenAccept { modelRenderable ->
                renderable = modelRenderable
                val toast = Toast.makeText(this@MainActivity,"Descarga completa, toque una superficie", Toast.LENGTH_LONG)
                toast.show()
            }
            .exceptionally { throwable ->
                val toast = Toast.makeText(this@MainActivity,"No se pudo descargar el elemnto 3D, compruebe su red", Toast.LENGTH_LONG)
                toast.show()
                return@exceptionally null
            }
    }
}