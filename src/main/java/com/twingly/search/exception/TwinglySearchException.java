package com.twingly.search.exception;

import com.twingly.search.domain.BlogStream;
import com.twingly.search.domain.Error;

/**
 * Custom runtime Twingly Exception
 */
public class TwinglySearchException extends RuntimeException {
    /**
     * Instantiates a new Twingly exception.
     */
    public TwinglySearchException() {
        super();
    }

    /**
     * Instantiates a new Twingly exception.
     *
     * @param error the error
     */
    public TwinglySearchException(Error error) {
        super(String.format("Error code: %s. Error message: %s", error.getCode(), error.getMessage()));
    }

    /**
     * Instantiates a new Twingly exception.
     *
     * @param blogStream the blog stream
     * @deprecated since 3.0.0
     */
    @Deprecated
    public TwinglySearchException(BlogStream blogStream) {
        this(String.format("resultType:%s, message:%s", blogStream.getOperationResult().getResultType(), blogStream.getOperationResult().getMessage()));
    }

    /**
     * Instantiates a new Twingly exception.
     *
     * @param message the message
     */
    public TwinglySearchException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Twingly exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public TwinglySearchException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Twingly exception.
     *
     * @param cause the cause
     */
    public TwinglySearchException(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new Twingly exception.
     *
     * @param message            the message
     * @param cause              the cause
     * @param enableSuppression  the enable suppression
     * @param writableStackTrace the writable stack trace
     */
    protected TwinglySearchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public static TwinglySearchException fromError(Error error) {
        if (error.getCode() != null) {
            if (error.getCode().startsWith("4")) {
                if (error.getCode().startsWith("400") || error.getCode().startsWith("404")) {
                    throw new TwinglySearchAuthenticationException(error);
                }
                if (error.getCode().startsWith("401") || error.getCode().startsWith("402")) {
                    throw new TwinglySearchQueryException(error);
                }
                throw new TwinglySearchClientException(error);
            }
            if (error.getCode().startsWith("5")) {
                throw new TwinglySearchServerException(error);
            }
            throw new TwinglySearchErrorException(error);
        }
        throw new TwinglySearchErrorException(error);
    }
}
