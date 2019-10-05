package jp.cordea.arcoredemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.Color
import com.google.ar.sceneform.rendering.MaterialFactory
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.rendering.ShapeFactory
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode

class MainActivity : AppCompatActivity() {
    private lateinit var redCube: ModelRenderable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fragment = supportFragmentManager.findFragmentById(R.id.ar_fragment) as ArFragment

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
            val node = AnchorNode(hitResult.createAnchor()).apply {
                setParent(fragment.arSceneView.scene)
            }
            TransformableNode(fragment.transformationSystem).apply {
                setParent(node)
                renderable = redCube
            }.select()
        }
    }
}
