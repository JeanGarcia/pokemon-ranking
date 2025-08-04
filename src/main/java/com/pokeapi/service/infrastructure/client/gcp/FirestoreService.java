package com.pokeapi.service.infrastructure.client.gcp;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.cloud.firestore.QuerySnapshot;
import com.pokeapi.service.domain.exception.FailToRetrievePokemonException;
import com.pokeapi.service.domain.model.Pokemon;
import com.pokeapi.service.domain.service.PokemonStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;


/**
 * FirestoreService
 *
 * @author Jean
 */
@Service
@Slf4j
public class FirestoreService implements PokemonStorage {

    @Value("${gcp.project-id}")
    private String projectId;

    @Value("${gcp.storage.firestore.collection-name}")
    private String collectionName;

    private final Firestore db;

    public FirestoreService() throws IOException {
        FirestoreOptions firestoreOptions =
                FirestoreOptions.getDefaultInstance().toBuilder()
                        .setProjectId(projectId)
                        .setCredentials(GoogleCredentials.getApplicationDefault())
                        .build();

        this.db = firestoreOptions.getService();
    }

    @Override
    public List<Pokemon> retrievePokemonList() {
        CollectionReference collection = db.collection(collectionName);
        QuerySnapshot snapshot;

        try {
            snapshot = collection.get().get();
        } catch (ExecutionException | InterruptedException e) {
            log.error("Error while retrieving pokemon list from FireStore: ", e);
            throw new FailToRetrievePokemonException("Error while retrieving pokemon list from FireStore: ", e);
        }
        return snapshot.getDocuments()
                .stream()
                .map(doc -> doc.toObject(Pokemon.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Pokemon> retrievePokemon(String pokemonId) {
        DocumentReference docRef = db.collection(collectionName).document(pokemonId);
        ApiFuture<DocumentSnapshot> future = docRef.get();

        try {
            DocumentSnapshot document = future.get();
            if (document.exists()) {
                return Optional.ofNullable(document.toObject(Pokemon.class));
            }
        } catch (ExecutionException | InterruptedException e) {
            log.error("Error while retrieving pokemon with id {} from FireStore: ", pokemonId, e);
            throw new FailToRetrievePokemonException("Error while retrieving pokemon with id" + pokemonId + " from FireStore: ", e);
        }

        return Optional.empty();
    }

    @Override
    public void savePokemon(Pokemon pokemon) {
        try {
            DocumentReference docRef = db.collection(collectionName).document(String.valueOf(pokemon.getId()));
            docRef.set(pokemon);
            log.info("Pokemon with id {} has been saved", pokemon.getId());
        } catch (Exception e) {
            log.error("Error while saving pokemon with id {} from FireStore: ", pokemon.getId(), e);
        }

    }

}
