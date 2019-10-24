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

package se.odengymnasiet.route;

import se.odengymnasiet.Controller;
import se.odengymnasiet.Manifest;
import spark.Request;
import spark.Response;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * A {@link RouteExecutor} which executes requests by invoked by the given
 * {@link Method} in the constructor.
 * @param <E> The {@link Controller} which this method is in.
 */
public class ReflectiveRouteExecutor<E extends Controller>
        implements RouteExecutor {

    private final Manifest<E> manifest;
    private final Method method;

    public ReflectiveRouteExecutor(Manifest<E> manifest, Method method) {
        this.manifest = manifest;
        this.method = method;
    }

    @Override
    public Object execute(String route,
                          Request request,
                          Response response) throws Exception {
        try {
            Object obj = this.getManifest().newController(request, response);
            if (obj != null) {
                return this.getMethod().invoke(obj);
            }
        } catch (InvocationTargetException e) {
            Throwable target = e.getTargetException();
            if (target instanceof Exception) {
                throw (Exception) target;
            } else {
                throw new Exception(target);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.status(404); // Not found
        return response.status();
    }

    public Manifest<E> getManifest() {
        return this.manifest;
    }

    public Method getMethod() {
        return this.method;
    }
}
