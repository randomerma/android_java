package com.example.practice2;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import java.util.Random;

public class Anecdote extends DialogFragment {

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String[] texts = {"— Доктор, от меня ушла собака!\n" +
                "— А что вы ей сказали?\n" +
                "— Сказал «служить», а она взяла каску и пошла в армию.\n" +
                "\n", "Плывут две акулы вдоль побережья и вдруг видят виндсерфингиста. Одна другой восхищенно: \n" +
                "— Вот это сервис — на подносе, да еще и с салфеткой!\n" +
                "\n", "Бесит, когда монстры под кроватью не стелят тебе постель\n" +
                "\n", "Преподаватель в возрасте \"бес в ребро\" объясняется студентке:\n" +
                "— За всю жизнь я любил только трех женщин: \n" +
                "— Маму, жену и, простите, как вас зовут?\n" +
                "\n","Дети, у которых были только печеньки и конфеты, из сладостей выложили слово «пить».\n" +
                "\n","До скелета динозавра опять докопались какие — то археологи.\n" +
                "\n","Вот сидит баба, сидит... С виду — ничего не делает.\n" +
                "А на самом деле бесится, хочет тортик и ненавидит своих бывших и бывших нынешнего.\n" +
                "Многозадачность...\n" +
                "\n"};


        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        return builder.setTitle("Анекдот").setMessage(texts[new Random().nextInt(texts.length)]).create();
    }
}
