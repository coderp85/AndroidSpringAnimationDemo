package com.crimsonw.physicsbasedanimations;

import android.content.Intent;
import android.os.Bundle;
import android.support.animation.DynamicAnimation;
import android.support.animation.SpringAnimation;
import android.support.animation.SpringForce;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import static android.support.animation.SpringForce.DAMPING_RATIO_HIGH_BOUNCY;
import static android.support.animation.SpringForce.DAMPING_RATIO_LOW_BOUNCY;
import static android.support.animation.SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY;
import static android.support.animation.SpringForce.STIFFNESS_LOW;

public class MainActivity extends AppCompatActivity {

    private ImageView midObject;
    private ImageView rightObject;
    private ImageView leftObject;
    private Button resetButton;
    private Button startButton;
    //Create Fixed Velocity Spring Animation
    private float finalPosition = 600f;
    private float velocityDp = 0.2f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        midObject = findViewById(R.id.midObject);
        rightObject = findViewById(R.id.rightObject);
        leftObject = findViewById(R.id.leftObject);
        resetButton = findViewById(R.id.resetButton);
        startButton = findViewById(R.id.startButton);

        setButtonClickListener();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_custom) {
            Intent intent = new Intent(this, CustomActivity.class);
            startActivity(intent);
            return true;
        }else if(id == R.id.action_chained_spring){
            Intent intent = new Intent(this, ChainedSpringActivity.class);
            startActivity(intent);
            return true;
        }
        return true;
    }

    private void setButtonClickListener() {

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLowBounceAnim(rightObject, velocityDp, finalPosition).start();
                getHighBounceAnim(leftObject, velocityDp, finalPosition).start();
                getMediumBounceAnim(midObject, velocityDp, finalPosition).start();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLowBounceAnim(rightObject, velocityDp, 0).start();
                getHighBounceAnim(leftObject, velocityDp, 0).start();
                getMediumBounceAnim(midObject, velocityDp, 0).start();
            }
        });

        rightObject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLowBounceAnim(rightObject, velocityDp, finalPosition).start();
            }
        });

        midObject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getMediumBounceAnim(midObject, velocityDp, finalPosition).start();
            }
        });

        leftObject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getHighBounceAnim(leftObject, velocityDp, finalPosition).start();
            }
        });

    }

    private SpringForce getSpringForce(float dampingRatio, float stiffness, float finalPosition) {
        SpringForce force = new SpringForce();
        force.setDampingRatio(dampingRatio).setStiffness(stiffness);
        force.setFinalPosition(finalPosition);
        return force;
    }

    private float getVelocity(float velocityDp) {
        //Get Velocity in pixels per second from dp per second
        return  TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, velocityDp,
                getResources().getDisplayMetrics());
    }

    private SpringAnimation getHighBounceAnim(ImageView view,  float velocityDp, float finalPosition ) {
        final SpringAnimation anim = new SpringAnimation(view, DynamicAnimation.TRANSLATION_Y);
        anim.setStartVelocity(getVelocity(velocityDp));
        anim.setSpring(getSpringForce(DAMPING_RATIO_HIGH_BOUNCY, STIFFNESS_LOW, finalPosition));
        return anim;
    }

    private SpringAnimation getMediumBounceAnim(ImageView view,  float velocityDp, float finalPosition ) {
        final SpringAnimation anim = new SpringAnimation(view, DynamicAnimation.TRANSLATION_Y);
        anim.setStartVelocity(getVelocity(velocityDp));
        anim.setSpring(getSpringForce(DAMPING_RATIO_MEDIUM_BOUNCY, STIFFNESS_LOW, finalPosition));
        return anim;
    }

    private SpringAnimation getLowBounceAnim(ImageView view,  float velocityDp, float finalPosition ) {
        final SpringAnimation anim = new SpringAnimation(view, DynamicAnimation.TRANSLATION_Y);
        anim.setStartVelocity(getVelocity(velocityDp));
        anim.setSpring(getSpringForce(DAMPING_RATIO_LOW_BOUNCY, STIFFNESS_LOW, finalPosition));
        return anim;
    }

}
