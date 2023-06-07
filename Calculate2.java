package com.example.clashroyaleproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class Calculate2 extends AppCompatActivity implements SpriteListRVAInterface, spriteSelectedRVAInterface{

    private RecyclerView spriteListRecyclerView, spriteSelectedRecyclerView;


    private RecyclerView.LayoutManager spriteListLayoutManager, spriteSelectedLayoutManager;
    
    private Dialog spriteListDialog, spriteAddDialog;

    private spriteListRecyclerViewAdapter rva1;
    private spriteSelectedRecyclerViewAdapter rva2;
    private int [] sprites_drawables = {R.drawable.giant_chest, R.drawable.gold_crate, R.drawable.golden_chest, R.drawable.overflowing_gold_crate, R.drawable.plentiful_gold_crate, R.drawable.silver_chest };
    private int [] maxAmountSelectedList = {10, 10, 10, 10, 10, 10};
    private int [] defaultAmountList = {5, 1, 1, 1, 1, 1};


    private ArrayList<Sprite> spritesList, spritesSelected;
    private ArrayList<Integer> spritesSelectedAmount;

    long startTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculate);

        spritesSelected = new ArrayList<Sprite>();
        spritesSelectedAmount = new ArrayList<Integer>();
        //put 5 giant chests in at the start


        spriteSelectedRecyclerView = findViewById(R.id.rv_selected_sprites);
        spriteSelectedLayoutManager = new GridLayoutManager(this, 3);
        spriteSelectedRecyclerView.setLayoutManager(spriteSelectedLayoutManager);
        //Creating new rva
        rva2 = new spriteSelectedRecyclerViewAdapter(this, spritesSelected, spritesSelectedAmount, this);
        //pairing rv and rva
        spriteSelectedRecyclerView.setAdapter(rva2);
        spriteSelectedRecyclerView.setHasFixedSize(true);

        spriteListDialog = new Dialog(this);



        TextView textView = findViewById(R.id.tvcalc);

        textView.setText(GlobalVar.arena + " " + GlobalVar.currency);


        //Create sprites here
        spritesList= new ArrayList<Sprite>();

        for (int i = 0; i < sprites_drawables.length; i++){
            spritesList.add(new Sprite(sprites_drawables[i], maxAmountSelectedList[i],defaultAmountList[i]));
        }


        getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainerView, CalculateFragment.class, null).commit();







    }
    public void showSpriteList(View v) {
        Button btnclose;


        //setting up pop-up window specifications
        spriteListDialog.setContentView(R.layout.add_sprites_popup);
        btnclose = spriteListDialog.findViewById(R.id.button_ok);
        

        spriteListRecyclerView = spriteListDialog.findViewById(R.id.rv_sprites);

        //setting the layoutmanager to be a grid layout
        spriteListLayoutManager = new GridLayoutManager(this, 2);
        spriteListRecyclerView.setLayoutManager(spriteListLayoutManager);

        //Creating new rva
        rva1 = new spriteListRecyclerViewAdapter(this, spritesList, this);

        //pairing rv and rva
        spriteListRecyclerView.setAdapter(rva1);

        spriteListRecyclerView.setHasFixedSize(true);

        btnclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spriteListDialog.dismiss();
            }
        });
        spriteListDialog.show();





    }


    @Override
    public int onAddButtonClick(Sprite s, int amount) {

        if (spritesSelected.size() != 0) {
            for (Sprite sprite : spritesSelected) {
                if (s == sprite) {
                    int i = spritesSelected.indexOf(sprite);
                    spritesSelectedAmount.set(i, spritesSelectedAmount.get(i) + amount);
                    rva2.notifyItemChanged(i);
                    return 1;

                }
            }
        }
        spritesSelected.add(s);
        spritesSelectedAmount.add(amount);


        rva2.notifyItemInserted(spritesSelected.size()-1);

        return 1;




        
    }

    @Override
    public void onItemViewClicked(Sprite sprite, Context context) {
        spriteAddDialog = new Dialog(context);
        spriteAddDialog.setContentView(R.layout.sprite_add_menu);
        spriteAddDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);



        ImageButton close = spriteAddDialog.findViewById(R.id.ib_close);
        EditText enter_amount = spriteAddDialog.findViewById(R.id.edt_enter_amount);
        Button add = spriteAddDialog.findViewById(R.id.b_add);

        enter_amount.setText(String.valueOf(sprite.defaultAmount));

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spriteAddDialog.dismiss();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int amountEntered = Integer.parseInt(enter_amount.getText().toString());
                int amountPresent = 0;
                try {
                    amountPresent =  spritesSelectedAmount.get(spritesSelected.indexOf(sprite));
                } catch (Exception IndexOutOfBoundsException){
                    //do nothing
                }
                if (amountEntered + amountPresent  > sprite.maxAmountSelected){
                    Toast.makeText(context, "Exceeded maximum limit of " + sprite.maxAmountSelected + " items.",
                            Toast.LENGTH_LONG).show();
                } else {

                    spriteAddDialog.dismiss();
                    spriteListDialog.dismiss();

                    //Get the SPRITE clicked somehow and pass it thru

                    onAddButtonClick(sprite, amountEntered);
                }


            }
        });

        spriteAddDialog.show();
    }



    @Override
    public void onItemClicked(int position) {
        rva2.notifyItemChanged(position);
    }

    @Override
    public void onDeleteButtonClicked(Sprite s) {
         s.setVisibility(false);
         int index = spritesSelected.indexOf(s);
         spritesSelected.remove(index);
         spritesSelectedAmount.remove(index);
         rva2.notifyItemRemoved(index);


    }

    public void deleteAll(View v){

        if (spritesSelected.size() != 0){
            int i = spritesSelected.size();

            spritesSelectedAmount.clear();
            spritesSelected.clear();


            rva2.notifyItemRangeRemoved(0, i);
        } else {
            Toast.makeText(this, "There's nothing to delete!",
                    Toast.LENGTH_LONG).show();
        }

    }
}
