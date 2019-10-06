package jp.cordea.arcoredemo

import android.content.Context
import android.widget.Button
import android.widget.SeekBar
import androidx.core.content.ContextCompat
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.Color
import com.google.ar.sceneform.rendering.MaterialFactory
import com.google.ar.sceneform.rendering.ShapeFactory
import com.google.ar.sceneform.rendering.ViewRenderable
import com.google.ar.sceneform.ux.TransformableNode
import com.google.ar.sceneform.ux.TransformationSystem

class ControllableCubeNode(
    private val context: Context,
    transformationSystem: TransformationSystem
) : TransformableNode(transformationSystem) {
    private var cubeNode: Node? = null
    private var itemNode: Node? = null

    override fun onActivate() {
        super.onActivate()

        if (cubeNode == null) {
            cubeNode = Node().also {
                it.setParent(this)
                it.localPosition = Vector3(0.0f, 1.0f, 0.0f)
            }
            ViewRenderable.builder()
                .setView(context, R.layout.ar_item)
                .build()
                .thenAccept {
                    cubeNode!!.renderable = it
                    val view = it.view
                    view.findViewById<Button>(R.id.button).setOnClickListener {
                        val scaleSeekBar = view.findViewById<SeekBar>(R.id.seek_bar_scale)
                        val rSeekBar = view.findViewById<SeekBar>(R.id.seek_bar_r)
                        val gSeekBar = view.findViewById<SeekBar>(R.id.seek_bar_g)
                        val bSeekBar = view.findViewById<SeekBar>(R.id.seek_bar_b)

                        val scale = scaleSeekBar.progress * 0.1f
                        itemNode?.localScale = Vector3(scale, scale, scale)
                        itemNode?.renderable?.material?.setFloat3(
                            MaterialFactory.MATERIAL_COLOR,
                            Color(
                                rSeekBar.progress / 100f,
                                gSeekBar.progress / 100f,
                                bSeekBar.progress / 100f
                            )
                        )
                    }
                }
        }

        if (itemNode == null) {
            itemNode = Node().also {
                it.setParent(this)
            }
            MaterialFactory.makeOpaqueWithColor(
                context,
                Color(
                    ContextCompat.getColor(
                        context,
                        R.color.colorPrimary
                    )
                )
            )
                .thenAccept { material ->
                    itemNode!!.renderable =
                        ShapeFactory.makeCube(Vector3(0.1f, 0.1f, 0.1f), Vector3.zero(), material)
                }
        }
    }
}
