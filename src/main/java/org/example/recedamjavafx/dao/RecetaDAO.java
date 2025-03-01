package org.example.recedamjavafx.dao;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.example.recedamjavafx.models.Favoritos;
import org.example.recedamjavafx.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RecetaDAO {

    // Método para buscar recetas en la BBDD
    public static List<Favoritos> buscarRecetasEnBaseDeDatos(String query) {
        List<Favoritos> favoritos = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Favoritos f WHERE f.nombre_receta LIKE :query";  // Filtrando por el nombre de la receta
            Query<Favoritos> hqlQuery = session.createQuery(hql, Favoritos.class);


            hqlQuery.setParameter("query", "%" + query + "%");

            // Limitar a 25 resultados o cualquier otro límite que quieras.
            hqlQuery.setMaxResults(25);  // Puedes cambiar el 25 por cualquier número que desees

            favoritos = hqlQuery.getResultList();
        } catch (Exception e) {
            e.printStackTrace();  // Solo mostramos el error, sin logger
        }
        return favoritos;
    }

    // Método para buscar recetas en la API de TheMealDB
    public static List<Favoritos> buscarRecetasEnAPI(String query) {
        List<Favoritos> favoritos = new ArrayList<>();
        try {
            // Construir la URL de búsqueda
            String apiUrl = "https://www.themealdb.com/api/json/v1/1/search.php?s=" + query;

            // Realizar la solicitud HTTP
            HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            // Leer la respuesta
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Parsear la respuesta JSON usando Gson
            JsonObject jsonResponse = JsonParser.parseString(response.toString()).getAsJsonObject();
            JsonArray meals = jsonResponse.getAsJsonArray("meals");

            if (meals != null) {
                for (int i = 0; i < meals.size(); i++) {
                    JsonObject meal = meals.get(i).getAsJsonObject();
                    Favoritos favorito = new Favoritos();

                    // Mapeamos los campos correspondientes de la API a la clase Favoritos
                    favorito.setNombre_receta(meal.get("strMeal").getAsString());
                    favorito.setImagenUrl(meal.get("strMealThumb").getAsString());
                    favorito.setInstruccion(meal.get("strInstructions").getAsString());

                    favoritos.add(favorito);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();  // Solo mostramos el error, sin logger
        }
        return favoritos;
    }


    // Método para buscar recetas en la base de datos y en la API
    public static List<Favoritos> buscarRecetas(String query, boolean buscarEnBaseDeDatos) {
        if (!buscarEnBaseDeDatos) {
            // Si no se desea buscar en la base de datos, solo se buscan recetas desde la API
            return buscarRecetasEnAPI(query);
        }

        // Primero, buscar en la base de datos
        List<Favoritos> favoritosLocales = buscarRecetasEnBaseDeDatos(query);

        // Si no se encuentran favoritos locales, buscar en la API
        if (favoritosLocales != null && !favoritosLocales.isEmpty()) {
            return favoritosLocales;
        } else {
            return buscarRecetasEnAPI(query);
        }
    }
}
