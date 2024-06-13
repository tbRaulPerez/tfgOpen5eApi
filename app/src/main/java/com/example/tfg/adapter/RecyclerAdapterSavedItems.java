package com.example.tfg.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tfg.R;
import com.example.tfg.controller.ArmorActivity;
import com.example.tfg.controller.BackgroundItemActivity;
import com.example.tfg.controller.CharacterActivity;
import com.example.tfg.controller.CharacterClassActivity;
import com.example.tfg.controller.CreatureActivity;
import com.example.tfg.controller.MagicItemActivity;
import com.example.tfg.controller.RaceActivity;
import com.example.tfg.controller.SpellActivity;
import com.example.tfg.controller.WeaponActivity;
import com.example.tfg.model.Armor;
import com.example.tfg.model.Background;
import com.example.tfg.model.Character;
import com.example.tfg.model.CharacterClass;
import com.example.tfg.model.Creature;
import com.example.tfg.model.MagicItem;
import com.example.tfg.model.Race;
import com.example.tfg.model.SavableItem;
import com.example.tfg.model.Spell;
import com.example.tfg.model.Weapon;

import org.json.JSONObject;

import java.util.List;

public class RecyclerAdapterSavedItems extends RecyclerView.Adapter<RecyclerAdapterSavedItems.SavedItemViewHolder>{
    List lista;
    public RecyclerAdapterSavedItems(List lista){
        this.lista = lista;
    }

    @NonNull
    @Override
    public SavedItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_character_item, parent, false);
        return new SavedItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedItemViewHolder holder, int position) {
        Object item =  lista.get(position);
        if(item instanceof Creature){
            Creature creature = (Creature) item;
            holder.txName.setText(creature.getName());
            holder.txDesc.setText("");
            if(creature.getSize() != null && !creature.getSize().equals("")){
                holder.txDesc.append(creature.getSize());
            }
            if(creature.getType() != null && !creature.getType().equals("")){
                holder.txDesc.append(" " + creature.getType());
            }
            if(creature.getAlignment() != null && !creature.getAlignment().equals("")){
                holder.txDesc.append(", " + creature.getAlignment());
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startListItemActivity(holder, CreatureActivity.class, item);
                }
            });
        }else if(item instanceof Character){
            Character character = (Character) item;
            holder.txName.setText(character.getName());
            holder.txDesc.setText("Lv " + character.getLevel() + " " + character.getcClass().getName());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startListItemActivity(holder, CharacterActivity.class, item);
                }
            });
        } else if(item instanceof Spell){
            Spell spell = (Spell) item;
            holder.txName.setText(spell.getName());
            holder.txDesc.setText(spell.getLevel() + " " + spell.getSchool());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startListItemActivity(holder, SpellActivity.class, item);
                }
            });
        }else if(item instanceof Background){
            Background background = (Background) item;
            holder.txName.setText(background.getName());
            holder.txDesc.setText("");
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startListItemActivity(holder, BackgroundItemActivity.class, item);
                }
            });
        }else if(item instanceof Race){
            Race race = (Race) item;
            holder.txName.setText(race.getName());
            holder.txDesc.setText("");
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startListItemActivity(holder, RaceActivity.class, item);
                }
            });
        }else if(item instanceof CharacterClass){
            CharacterClass cClass = (CharacterClass) item;
            holder.txName.setText(cClass.getName());
            holder.txDesc.setText("");
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startListItemActivity(holder, CharacterClassActivity.class, item);
                }
            });
        }
        else if(item instanceof MagicItem){
            MagicItem magicItem = (MagicItem) item;
            holder.txName.setText(magicItem.getName());
            holder.txDesc.setText(magicItem.getRarity() + " " + magicItem.getType());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startListItemActivity(holder, MagicItemActivity.class, item);
                }
            });
        }else if(item instanceof Weapon){
            Weapon weapon = (Weapon) item;
            holder.txName.setText(weapon.getName());
            holder.txDesc.setText(weapon.getCategory());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startListItemActivity(holder, WeaponActivity.class, item);
                }
            });
        }else if(item instanceof Armor){
            Armor armor = (Armor) item;
            holder.txName.setText(armor.getName());
            holder.txDesc.setText(armor.getAcString());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startListItemActivity(holder, ArmorActivity.class, item);
                }
            });
        }

    }
    //Lanza una actividad recibiendo por parametro el RecyclerHolder, la clase de la actividad, y la url
    public void startListItemActivity(SavedItemViewHolder holder, Class activityClass, Object object){
        Intent intent = new Intent(holder.itemView.getContext(), activityClass);
        if(object != null){
            SavableItem item = (SavableItem) object;
            intent.putExtra("OBJECTFROMFIREBASE", item);
        }
        holder.itemView.getContext().startActivity(intent);

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }
    class SavedItemViewHolder extends RecyclerView.ViewHolder{
        TextView txName, txDesc;

        public SavedItemViewHolder(@NonNull View itemView) {
            super(itemView);
            txName = itemView.findViewById(R.id.txCharacterListName);
            txDesc = itemView.findViewById(R.id.txCharacterListDesc);
        }
    }
}
