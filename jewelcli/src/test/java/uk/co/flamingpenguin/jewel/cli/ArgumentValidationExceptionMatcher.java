package uk.co.flamingpenguin.jewel.cli;

import static ch.lambdaj.Lambda.selectFirst;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import uk.co.flamingpenguin.jewel.cli.ArgumentValidationException.ValidationError;
import uk.co.flamingpenguin.jewel.cli.ArgumentValidationException.ValidationError.ErrorType;

/*
 * Copyright 2011 Tim Wood
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */

public class ArgumentValidationExceptionMatcher {
    public static Matcher<ArgumentValidationException> validationException(final ErrorType expectedErrorType) {
        return new TypeSafeMatcher<ArgumentValidationException>() {
            @Override public void describeTo(final Description description) {
                description
                        .appendText(ArgumentValidationException.class.getSimpleName())
                        .appendText(" with error type ")
                        .appendValue(expectedErrorType);
            }

            @Override protected boolean matchesSafely(final ArgumentValidationException item) {
                return selectFirst(item.getValidationErrors(), new TypeSafeMatcher<ValidationError>() {
                    @Override public void describeTo(final Description description) {
                        description
                                .appendText(ArgumentValidationException.ValidationError.class.getSimpleName())
                                .appendText(" with error type ")
                                .appendValue(expectedErrorType);
                    }

                    @Override protected boolean matchesSafely(final ValidationError actualError) {
                        return actualError.getErrorType().equals(expectedErrorType);
                    }
                }) != null;
            }
        };
    }
}
