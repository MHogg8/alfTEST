package example.codeclan.com.alforii;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class ShowImage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);
        ImageView img=(ImageView)findViewById(R.id.clock);

        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        findViewById(R.id.clock).startAnimation(shake);

    }

}
