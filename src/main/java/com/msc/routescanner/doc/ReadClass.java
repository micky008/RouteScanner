package com.msc.routescanner.doc;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 *
 * @author Michael
 */
class ReadClass {

    private final Class clazz;

    public ReadClass(Class clazz) {
        this.clazz = clazz;
    }

    public List<Route> getRoutes() {
        List<Route> routes = new ArrayList<>();

        Path p = (Path) clazz.getAnnotation(Path.class);

        Route route = new Route();
        route.setRoute(p.value());
        route.setIsHead(true);
        route.setResponse(this.clazz.getSimpleName());
        routes.add(route);

        Method ms[] = clazz.getDeclaredMethods();
        for (Method m : ms) {
            route = new Route();
            for (Annotation a : m.getAnnotations()) {
                if (putMethod(a, route)) {
                    continue;
                }
                if (putConsume(m, a, route)) {
                    continue;
                }
                if (putProduces(m, a, route)) {
                    continue;
                }
                putPath(m, a, route);
            }
            putInputParameter(m, route);
            getRetourType(m, route);
            routes.add(route);
        }

        return routes;
    }

    private boolean putMethod(Annotation a, Route r) {
        if (a.annotationType() == GET.class) {
            r.setMethod(GET.class.getSimpleName());
        } else if (a.annotationType() == POST.class) {
            r.setMethod(POST.class.getSimpleName());
        } else if (a.annotationType() == PUT.class) {
            r.setMethod(PUT.class.getSimpleName());
        } else if (a.annotationType() == DELETE.class) {
            r.setMethod(DELETE.class.getSimpleName());
        }
        return r.getMethod() == null;
    }

    private boolean putConsume(Method m, Annotation a, Route r) {
        if (a.annotationType() == Consumes.class) {
            Consumes c = m.getAnnotation(Consumes.class);
            if (r.getConsumes() == null) {
                r.setConsumes(new ArrayList<String>());
            }
            for (String s : c.value()) {
                r.getConsumes().add(s);
            }
            return true;
        }
        return false;
    }

    private boolean putProduces(Method m, Annotation a, Route r) {
        if (a.annotationType() == Produces.class) {
            Produces c = m.getAnnotation(Produces.class);
            if (r.getProduces() == null) {
                r.setProduces(new ArrayList<String>());
            }
            for (String s : c.value()) {
                r.getProduces().add(s);
            }
            return true;
        }
        return false;
    }

    private boolean putPath(Method m, Annotation a, Route r) {
        if (a.annotationType() == Path.class) {
            Path c = m.getAnnotation(Path.class);
            r.setRoute(c.value());
            return true;
        }
        return false;
    }

    private void putInputParameter(Method m, Route r) {
        if (m.getParameters().length == 0) {
            return;
        }
        if (r.getInputs() == null) {
            r.setInputs(new ArrayList<String>());
        }
        for (Parameter p : m.getParameters()) {
            String input = recursMethode(p.getType().getSimpleName(), p.getParameterizedType());
            for (Annotation a : p.getAnnotations()) {
                if (a.annotationType() == PathParam.class) {
                    PathParam pp = p.getAnnotation(PathParam.class);
                    input += " " + pp.value();
                }
            }
            r.getInputs().add(input);
        }
    }

    private void getRetourType(Method m, Route r) {
        Type type = m.getGenericReturnType();
        String res = m.getReturnType().getSimpleName();
        String res2 = recursMethode(res, type);
        r.setResponse(res2);
    }

    private String recursMethode(String next, Type type) {
        if (type instanceof ParameterizedType) {
            next += "<";
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type[] typeArguments = parameterizedType.getActualTypeArguments();
            int i = 0;
            for (Type typeArgument : typeArguments) {
                if (typeArgument instanceof ParameterizedType) {
                    Type t = ((ParameterizedType) typeArgument).getRawType();
                    next += recursMethode(getLastPart(t), typeArgument);
                    continue;
                }
                if (typeArgument instanceof WildcardType) {
                    continue;
                }
                Class typeArgClass = (Class) typeArgument;
                next += typeArgClass.getSimpleName();
                i++;
                if (typeArguments.length > i) {
                    next += ",";
                }
            }
            next += ">";
        }
        return next;
    }

    private String getLastPart(Type type) {
        String typeStr = type.getTypeName();
        return typeStr.substring(typeStr.lastIndexOf(".") + 1);
    }

}
