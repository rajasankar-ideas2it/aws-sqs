package com.powerplay;

import java.util.UUID;

import com.powerplay.exception.PublishException;

/**
 * Publishing interface which provides strong delivery guarantees of objects published via
 * {@link #publish(Object)}.
 *
 * @author powerplay.
 * @param <T>
 *        The type of object being published
 */
public interface Publisher<T> {

    /**
     * Publishes an item to the publisher service for processing. The publishing service is
     * responsible for generating a unique identifier for the item being published. Unless a
     * {@code PublishException} is thrown, the {@code PublishService} assumes responsibility for the
     * published object and guarantees its delivery to its subscriber.
     *
     * @param item
     *        item to Send
     * @return The tracking ID of the published message
     */
    UUID publish(T item) throws PublishException;

}
