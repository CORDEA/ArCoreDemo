package jp.cordea.arcoredemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.ar.core.HitResult
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.*
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode

class MainActivity : AppCompatActivity() {
    private lateinit var fragment: ArFragment
    private lateinit var redCube: ModelRenderable
    private lateinit var item: ViewRenderable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fragment = supportFragmentManager.findFragmentById(R.id.ar_fragment) as ArFragment

        ViewRenderable.builder()
            .setView(this, R.layout.ar_item)
            .build()
            .thenAccept { item = it }

        MaterialFactory.makeOpaqueWithColor(
            this,
            Color(
                ContextCompat.getColor(
                    this,
                    R.color.colorPrimary
                )
            )
        )
            .thenAccept { material ->
                redCube =
                    ShapeFactory.makeCube(Vector3(0.1f, 0.1f, 0.1f), Vector3.zero(), material)
            }

        fragment.setOnTapArPlaneListener { hitResult, _, _ ->
            addRenderable(hitResult, item)
            addRenderable(hitResult, redCube)
        }
    }

    private fun addRenderable(hitResult: HitResult, renderable: Renderable) {
        val node = AnchorNode(hitResult.createAnchor()).apply {
            setParent(fragment.arSceneView.scene)
        }
        TransformableNode(fragment.transformationSystem).apply {
            setParent(node)
            this.renderable = renderable
        }
    }
}
