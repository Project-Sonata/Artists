package com.odeyalo.sonata.artists.service.event;

import com.odeyalo.sonata.suite.brokers.events.SonataEvent;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The {@code EmittingEventSource} interface represents an event source capable of emitting events. It extends the base
 * {@link EventSource} interface, providing additional functionality to directly emit events to the event stream.
 *
 * <p>Implementations of this interface combine the capabilities of an event source (offering the stream of events) and
 * the ability to emit specific events directly. Subscribers can listen to the event stream using the inherited
 * {@link #getEvents()} method, and the event source can emit events to the stream using the {@link #emitNext(SonataEvent)}
 * method.
 *
 * <p>The {@link #emitNext(SonataEvent)}} method allows the event source to publish specific events to all registered listeners.
 * When an event is emitted, all subscribers of the event source will receive the event through the event stream. This
 * feature enables event sources to act as producers of events, efficiently notifying all interested listeners about
 * specific occurrences.
 *
 * @implSpec Implementations of this interface should ensure that the event stream returned by {@link #getEvents()}
 *           remains active and emits events as long as the event source is operational. Event sources should handle any
 *           errors that may occur internally and not propagate those errors to the subscribers through the event stream.
 *
 * @see EventSource
 * @see Flux
 * @see DefaultEventSource
 */
public interface EmittingEventSource extends EventSource {
    /**
     * Emit the specified event to all registered listeners. Subscribers will receive this event through the event
     * stream.
     *
     * <p>When this method is used, the event is directly added to the event stream of the event source. All registered
     * listeners will receive the event asynchronously and non-blocking, allowing for reactive processing of the event.
     *
     * @param event the event to be emitted by the event source.
     *
     * @throws IllegalArgumentException if the provided event is null.
     */
    Mono<Void> emitNext(SonataEvent event);
}
