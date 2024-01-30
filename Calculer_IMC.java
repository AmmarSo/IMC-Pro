package com.killapp.imcpro;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class Calculer_IMC extends AppCompatActivity {

    private TextInputEditText editTextAge, editTextWeight, editTextHeight;
    private RadioGroup radioGroupGender;
    private TextView textViewResult;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculerimc);

        editTextAge = findViewById(R.id.editTextAge);
        editTextWeight = findViewById(R.id.editTextWeight);
        editTextHeight = findViewById(R.id.editTextHeight);
        radioGroupGender = findViewById(R.id.radioGroupGender);
        Button buttonCalculate = findViewById(R.id.buttonCalculate);
        textViewResult = findViewById(R.id.textViewResult);
        buttonCalculate.setOnClickListener(v -> calculateBMI());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void calculateBMI() {
        String ageString = Objects.requireNonNull(editTextAge.getText()).toString();
        String weightString = Objects.requireNonNull(editTextWeight.getText()).toString();
        String heightString = Objects.requireNonNull(editTextHeight.getText()).toString();

        if (ageString.isEmpty() || weightString.isEmpty() || heightString.isEmpty()) {
            Toast.makeText(this, "Veuillez saisir toutes les valeurs", Toast.LENGTH_SHORT).show();
            return;
        } else if (Float.parseFloat(ageString) == 0 || Float.parseFloat(weightString) == 0 || Float.parseFloat(heightString) == 0) {
            Toast.makeText(this, "Les valeurs ne peuvent pas être égales à zéro", Toast.LENGTH_SHORT).show();
            return;
        }

        int age = Integer.parseInt(ageString);
        float weight = Float.parseFloat(weightString);
        float height = Float.parseFloat(heightString) / 100;

        int selectedGenderId = radioGroupGender.getCheckedRadioButtonId();
        RadioButton selectedGenderRadioButton = findViewById(selectedGenderId);
        String gender = selectedGenderRadioButton.getText().toString();

        int bmi = (int) calculateBMIValue(weight, height);
        float heightSquared = (float) Math.pow(Float.parseFloat(heightString) / 100, 2);
        int poids_minimal = (int) (heightSquared * (19.5));
        int poids_maximal = (int) (heightSquared * (24.5));

        String message = "<html><body><div style='text-align:center;'>Votre Indice de Masse Corporelle (IMC) est " + bmi + "</div>";

        if (age >= 12) {
            if (gender.equals("Homme")) {
                if (bmi < 14) {
                    message += "<div style='text-align:center;'>Whoa ! Vous êtes tellement léger que vous pourriez voler au moindre souffle de vent ! 🌬️</div>";
                    message += "<div style='text-align:center;'>Mais ne vous inquiétez pas, l'important est d'être en bonne santé. Visez un poids entre " + poids_minimal + " kg et " + poids_maximal + " kg pour être au top !</div><br>";
                } else if (bmi < 18.5) {
                    message += "<div style='text-align:center;'> Insuffisance Pondérale <br> Pas de panique, vous êtes juste un super modèle d'économie d'énergie ! ⚡</div>";
                    message += "<div style='text-align:center;'>Mais rappelez-vous, manger sainement vous rendra encore plus radieux. Visez un poids entre " + poids_minimal + " kg et " + poids_maximal + " kg pour briller davantage !</div><br>";
                } else if (bmi < 25) {
                    message += "<div style='text-align:center;'>Normal <br>Félicitations, votre poids est normal ! 🎉</div>";
                    message += "<div style='text-align:center;'>Vous êtes déjà une star, continuez à briller en gardant cet IMC sain. Vous êtes incroyable !</div><br>";
                } else if (bmi < 30) {
                    message += "<div style='text-align:center;'>Surpoids <br>Vous êtes en mode légèrement enveloppé, mais c'est juste plus de vous à aimer ! 🥰</div>";
                    message += "<div style='text-align:center;'>Un petit objectif : visez un poids entre " + poids_minimal + " kg et " + poids_maximal + " kg pour être le plus cool possible !</div><br>";
                } else if (bmi < 35) {
                    message += "<div style='text-align:center;'>Obésité modérée <br>Vous avez grignoté quelques pizzas supplémentaires, mais qui peut résister à une bonne pizza ? 🍕</div>";
                    message += "<div style='text-align:center;'>Amusez-vous tout en gardant un œil sur votre santé. Visez un poids entre " + poids_minimal + " kg et " + poids_maximal + " kg pour être au top !</div><br>";
                } else if (bmi < 40) {
                    message += "<div style='text-align:center;'>Obésité sévère <br>Vous avez conquis l'obésité sévère, mais rappelez-vous que vous êtes bien plus qu'un simple chiffre ! ❤️</div>";
                    message += "<div style='text-align:center;'>Prenez soin de vous, c'est le moment de chouchouter votre corps. Visez un poids entre " + poids_minimal + " kg et " + poids_maximal + " kg pour être en pleine forme !</div><br>";
                } else if (bmi < 185) {
                    message += "<div style='text-align:center;'>Obésité morbide<br>Vous êtes officiellement en obésité morbide ! Mais ne vous inquiétez pas, nous allons vous transformer en 'Obésité Survivant' ! 🦸</div>";
                    message += "<div style='text-align:center;'>Un pas à la fois, concentrez-vous sur des choix de vie sains et vous pouvez accomplir de grandes choses. Visez un poids entre " + poids_minimal + " kg et " + poids_maximal + " kg pour devenir un héros !</div><br>";
                } else {
                    message += "<div style='text-align:center;'>Wow ! Vous avez un IMC qui nécessite une consultation avec un médecin. Mais vous êtes unique, une vraie énigme à résoudre ! 🔍</div>";
                    message += "<div style='text-align:center;'>Prenez soin de vous et demandez l'aide d'un professionnel pour vous guider vers une santé optimale. Vous êtes précieux !</div><br>";
                }
            } else if (gender.equals("Femme")) {
                if (bmi < 14) {
                    message += "<div style='text-align:center;'>Whoa ! Vous êtes tellement légère que vous pourriez danser sur l'eau ! 💃</div>";
                    message += "<div style='text-align:center;'>Mais sérieusement, pensez à manger sainement pour garder votre corps en pleine forme. Visez un poids entre " + poids_minimal + " kg et " + poids_maximal + " kg pour briller davantage !</div><br>";
                } else if (bmi < 18.5) {
                    message += "<div style='text-align:center;'>Insuffisance Pondérale <br> Pas de panique, vous êtes simplement une experte en apesanteur ! 🚀</div>";
                    message += "<div style='text-align:center;'>Mais n'oubliez pas de vous chouchouter et de manger sainement pour rayonner encore plus. Visez un poids entre " + poids_minimal + " kg et " + poids_maximal + " kg pour être au top !</div><br>";
                } else if (bmi < 24) {
                    message += "<div style='text-align:center;'>Normal <br>Félicitations, votre poids est normal ! 🎉</div>";
                    message += "<div style='text-align:center;'>Vous êtes déjà une étoile étincelante, continuez à briller en gardant cet IMC sain. Vous êtes incroyable !</div><br>";
                } else if (bmi < 29) {
                    message += "<div style='text-align:center;'>Surpoids <br> Vous êtes en mode légèrement enveloppée, mais cela ne fait qu'ajouter à votre charme ! 🌟</div>";
                    message += "<div style='text-align:center;'>Gardez le sourire et prenez soin de vous pour être la plus belle. Visez un poids entre " + poids_minimal + " kg et " + poids_maximal + " kg pour être au top !</div><br>";
                } else if (bmi < 34) {
                    message += "<div style='text-align:center;'>Obésité modérée <br>Vous avez savouré quelques gourmandises de plus, mais qui peut résister à des gourmandises délicieuses ? 🍫</div>";
                    message += "<div style='text-align:center;'>Amusez-vous tout en gardant un œil sur votre santé. Visez un poids entre " + poids_minimal + " kg et " + poids_maximal + " kg pour être en pleine forme !</div><br>";
                } else if (bmi < 39) {
                    message += "<div style='text-align:center;'>Obésité sévère <br>Vous avez conquis l'obésité sévère, mais rappelez-vous que vous êtes bien plus qu'un simple chiffre ! ❤️</div>";
                    message += "<div style='text-align:center;'>Prenez soin de vous, c'est le moment de chouchouter votre corps. Visez un poids entre " + poids_minimal + " kg et " + poids_maximal + " kg pour être en pleine forme !</div><br>";
                } else if (bmi < 185) {
                    message += "<div style='text-align:center;'>Obésité morbide<br>Vous êtes officiellement en obésité morbide ! Mais ne vous inquiétez pas, nous allons vous transformer en 'Obésité Survivante' ! 🦸</div>";
                    message += "<div style='text-align:center;'>Un pas à la fois, concentrez-vous sur des choix de vie sains et vous pouvez accomplir de grandes choses. Visez un poids entre " + poids_minimal + " kg et " + poids_maximal + " kg pour devenir une héroïne !</div><br>";
                } else {
                    message += "<div style='text-align:center;'>Wow ! Vous avez un IMC qui nécessite une consultation avec un médecin. Mais vous êtes unique, une vraie énigme à résoudre ! 🔍</div>";
                    message += "<div style='text-align:center;'>Prenez soin de vous et demandez l'aide d'un professionnel pour vous guider vers une santé optimale. Vous êtes précieuse !</div><br>";
                }
            }
        } else {
            message = "<div style='text-align:center;'><font color=\"#FF0000\"><br>Attention : Ces calculs ne fonctionnent pas sur des personnes âgées de moins de 12 ans.</font></div>";
        }

        // Afficher le résultat
        textViewResult.setText(Html.fromHtml(message));

        // Sauvegarder l'IMC calculé
        saveIMC(bmi);
    }

    private void saveIMC(float bmi) {
        SharedPreferences sharedPreferences = getSharedPreferences("IMC_HISTORY", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String history = sharedPreferences.getString("IMC_VALUES", "");
        String currentDate = String.valueOf(System.currentTimeMillis());
        history = currentDate + "," + bmi + "," + history;

        editor.putString("IMC_VALUES", history);

        // Limiter l'historique à 8 valeurs
        String[] values = history.split(",");
        if (values.length > 24) { // 3 elements per entry (date, bmi, comma)
            history = history.substring(0, history.lastIndexOf(",", history.lastIndexOf(",") - 1));
            editor.putString("IMC_VALUES", history);
        }

        editor.apply();
    }


    private float calculateBMIValue(float weight, float height) {
        return weight / (height * height);
    }
}
