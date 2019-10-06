package jp.cordea.arcoredemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.ux.ArFragment

class MainActivity : AppCompatActivity() {
    private lateinit var fragment: ArFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fragment = supportFragmentManager.findFragmentById(R.id.ar_fragment) as ArFragment

        fragment.setOnTapArPlaneListener { hitResult, _, _ ->
            val node = AnchorNode(hitResult.createAnchor()).apply {
                setParent(fragment.arSceneView.scene)
            }
            ControllableCubeNode(this).apply {
                setParent(node)
            }
        }
    }
}
