package jp.cordea.arcoredemo

import android.content.Context
import android.widget.Button
import androidx.core.content.ContextCompat
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.Color
import com.google.ar.sceneform.rendering.MaterialFactory
import com.google.ar.sceneform.rendering.ShapeFactory
import com.google.ar.sceneform.rendering.ViewRenderable

class ControllableCubeNode(private val context: Context) : Node() {
    private var cubeNode: Node? = null
    private var itemNode: Node? = null

    override fun onActivate() {
        super.onActivate()

        if (cubeNode == null) {
            cubeNode = Node().also {
                it.setParent(this)
            }
            ViewRenderable.builder()
                .setView(context, R.layout.ar_item)
                .build()
                .thenAccept {
                    cubeNode!!.renderable = it
                    val view = it.view
                    view.findViewById<Button>(R.id.button).setOnClickListener {
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
