package rocks.teagantotally.deepthought_routing.helpers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import dalvik.system.DexFile;
import dalvik.system.PathClassLoader;
import timber.log.Timber;

/**
 * Created by tglenn on 10/1/17.
 */

public abstract class ClassHelper {
    /**
     * Gets all loaded classes with the given annotation(s)
     *
     * @param context     Context
     * @param annotations Annotation types to check for
     * @return Set of annotated classes
     */
    public static Set<Class<?>> getClassesWithAnnotation(@NonNull Context context,
                                                         @Nullable Set<String> packagesToInclude,
                                                         Class<? extends Annotation>... annotations) {
        Objects.requireNonNull(context,
                               "Context cannot be null");
        if (annotations == null || annotations.length == 0) {
            Timber.d("No annotations specified");
            return new HashSet<>();
        }

        Set<Class<?>> classes = new HashSet<>();
        String packageCodePath = context.getPackageCodePath();
        PathClassLoader classLoader = (PathClassLoader) Thread.currentThread()
                                                              .getContextClassLoader();

        boolean includeAllPackages = packagesToInclude == null || packagesToInclude.isEmpty();

        if (includeAllPackages) {
            Timber.w("Including annotated classes from any package");
        }

        @SuppressWarnings("deprecation") DexFile dexFile;
        try {
            dexFile = new DexFile(packageCodePath);
        } catch (IOException e) {
            Timber.w("Unable to load DexFile %s",
                     packageCodePath,
                     e);
            return classes;
        }

        Enumeration<String> entries = dexFile.entries();
        while (entries.hasMoreElements()) {
            String entry = entries.nextElement();
            Class<?> entryClass = dexFile.loadClass(entry,
                                                    classLoader);
            if (entryClass == null) {
                continue;
            }

            if (!includeAllPackages && !isClassInPackagesToInclude(entryClass,
                                                                   packagesToInclude)) {
                continue;
            }

            for (Class<? extends Annotation> annotation : annotations) {
                if (!entryClass.isAnnotationPresent(annotation)) {
                    continue;
                }

                classes.add(entryClass);
            }
        }

        return classes;
    }

    private static boolean isClassInPackagesToInclude(Class<?> classToCheck,
                                                      Set<String> packagesToInclude) {
        String classPackage = classToCheck.getPackage()
                                          .getName();
        if (TextUtils.isEmpty(classPackage)) {
            Timber.d("Class %s has empty package name",
                     classToCheck.getName());
            return false;
        }

        for (String packageToInclude : packagesToInclude) {
            if (classPackage.toLowerCase()
                            .startsWith(packageToInclude.toLowerCase())) {
                return true;
            }
        }

        return false;
    }
}
