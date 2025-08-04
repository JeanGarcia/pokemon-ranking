package com.pokeapi.service.infrastructure.client.gcp;

import com.google.cloud.tasks.v2.CloudTasksClient;
import com.google.cloud.tasks.v2.HttpMethod;
import com.google.cloud.tasks.v2.HttpRequest;
import com.google.cloud.tasks.v2.QueueName;
import com.google.cloud.tasks.v2.Task;
import com.google.protobuf.ByteString;
import com.pokeapi.service.domain.service.PokemonNotification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TaskNotificationService
 *
 * @author Jean
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TaskNotificationService implements PokemonNotification {
    @Value("${gcp.project-id}")
    private String projectId;

    @Value("${gcp.task.queue.location}")
    private String location;

    @Value("${gcp.task.queue}")
    private String queue;

    @Override
    public void sendPokemonSyncNotificationBulk(List<String> pokemonIds, String targetEndpoint) {
        try (CloudTasksClient client = CloudTasksClient.create()) {
            QueueName queueName = QueueName.of(projectId, location, queue);

            for (String pokemonId : pokemonIds) {
                log.info("Sending Notification for Pokemon: {}", pokemonId);
                HttpRequest httpRequest = HttpRequest.newBuilder()
                        .setUrl(targetEndpoint)
                        .setHttpMethod(HttpMethod.POST)
                        .putHeaders("Content-Type", "application/json")
                        .setBody(ByteString.copyFromUtf8("{\"pokemon_id\":\"" + pokemonId + "\"}"))
                        .build();
                Task task = Task.newBuilder().setHttpRequest(httpRequest).build();
                client.createTask(queueName, task);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
