/*
 * Copyright 2019 Aleksander Jagiełło <themolkapl@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package se.odengymnasiet;

/**
 * Handling error pages. Currently only HTTP 404 (not found) and HTTP 500
 * (internal server error) are supported.
 */
public class ErrorPage {

    public static final String VIEW_NAME = "error";

    public static final String MESSAGE_NOT_FOUND = "Sidan hittades inte!";
    public static final String MESSAGE_INTERNAL_ERROR = "Internt serverfel!";

    private final Application app;

    private String code;
    private String message;

    public ErrorPage(Application app) {
        this.app = app;
    }

    public ErrorPage(Application app, String code, String message) {
        this(app);

        this.code = code;
        this.message = message;
    }

    public ErrorPage(Application app, int code, String message) {
        this(app, Integer.toString(code), message);
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public String getTitle() {
        return this.getCode() + " - " + this.getMessage();
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setCode(int code) {
        this.setCode(Integer.toString(code));
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String render() {
        return this.app.renderView(VIEW_NAME, Attributes.create("error", this));
    }
}
