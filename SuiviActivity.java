package com.killapp.imcpro;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SuiviActivity extends AppCompatActivity {

    private XYPlot xyPlot;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suivi);

        xyPlot = findViewById(R.id.xyPlot);
        plotIMCEvolution();
    }

    private void plotIMCEvolution() {
        // Récupérer les valeurs d'IMC enregistrées dans les SharedPreferences
        List<Float> imcValues = getIMCValues();

        // Vérifier s'il y a suffisamment de valeurs pour tracer le graphique
        if (imcValues.size() >= 3) {
            // Créer une liste de séries pour le graphique
            List<Date> dates = generateDates(imcValues.size());
            XYSeries series = new SimpleXYSeries(dates, imcValues, "IMC");

            // Créer un formatteur pour la série de données
            LineAndPointFormatter formatter = new LineAndPointFormatter(
                    this, R.xml.line_point_formatter_with_labels);

            // Ajouter la série de données au graphique
            xyPlot.addSeries(series, formatter);

            // Personnaliser l'apparence du graphique
            xyPlot.getGraph().getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).setFormat(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()));
            xyPlot.getGraph().getLineLabelStyle(XYGraphWidget.Edge.LEFT).setFormat(new DecimalFormat("#.#"));

            // Redessiner le graphique
            xyPlot.redraw();
        } else {
            Toast.makeText(this, "Pas assez de mesures pour tracer le graphique", Toast.LENGTH_SHORT).show();
        }
    }

    private List<Float> getIMCValues() {
        // Récupérer les valeurs d'IMC enregistrées depuis les SharedPreferences
        List<Float> imcValues = new ArrayList<>();
        SharedPreferences sharedPreferences = getSharedPreferences("IMC_HISTORY", MODE_PRIVATE);
        String history = sharedPreferences.getString("IMC_VALUES", "");

        if (!history.isEmpty()) {
            String[] entries = history.split(",");
            for (int i = 1; i < entries.length; i += 3) {
                float imc = Float.parseFloat(entries[i]);
                imcValues.add(imc);
            }
        }

        return imcValues;
    }

    private List<Date> generateDates(int count) {
        List<Date> dates = new ArrayList<>();
        long currentTime = System.currentTimeMillis();
        for (int i = count - 1; i >= 0; i--) {
            Date date = new Date(currentTime - i * 86400000L); // 86400000L = 1 jour en millisecondes
            dates.add(date);
        }
        return dates;
    }
}
