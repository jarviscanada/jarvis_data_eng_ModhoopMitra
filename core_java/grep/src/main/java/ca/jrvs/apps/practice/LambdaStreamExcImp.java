package ca.jrvs.apps.practice;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LambdaStreamExcImp implements LambdaStreamExc {

    public static void main(String[] args) {
        LambdaStreamExc lse = new LambdaStreamExcImp();

        Supplier<Stream<String>> strStreamSupplier = () -> lse.createStrStream("one", "two", "Three");
        strStreamSupplier.get().forEach(System.out::println);

        (lse.toUpperCase("one")).forEach(System.out::println);

        lse.filter(strStreamSupplier.get(), "n").forEach(System.out::println);

        int[] arr = {1, 2, 3};
        lse.createIntStream(arr).forEach(System.out::println);

        lse.toList(strStreamSupplier.get()).forEach(System.out::println);

        System.out.println(lse.toList(lse.createIntStream(arr)));

        lse.createIntStream(1,5).forEach(System.out::println);

        lse.squareRootIntStream(lse.createIntStream(arr)).forEach(System.out::println);

        lse.getOdd(lse.createIntStream(1,5)).forEach(System.out::println);

        lse.getLambdaPrinter("start>", "<end").accept("Message body");

        String[] messages = {"1", "2", "3"};
        lse.printMessages(messages, lse.getLambdaPrinter("a", "c"));

        lse.printOdd(lse.createIntStream(arr), lse.getLambdaPrinter("number: ", " is odd."));

        List<List<Integer>> ints = Arrays.asList(
                Arrays.asList(1, 2),
                Arrays.asList(3, 4),
                Arrays.asList(5, 6));
        lse.flatNestedInt(ints.stream()).forEach(System.out::println);

    }

    @Override
    public Stream<String> createStrStream(String... strings) {
        return Stream.of(strings);
    }

    @Override
    public Stream<String> toUpperCase(String... strings) {
        return createStrStream(strings).map(String::toUpperCase);
    }

    @Override
    public Stream<String> filter(Stream<String> stringStream, String pattern) {
        return stringStream.filter(s -> s.contains(pattern));
    }

    @Override
    public IntStream createIntStream(int[] arr) {
        return Arrays.stream(arr);
    }

    @Override
    public <E> List<E> toList(Stream<E> stream) {
        return stream.collect(Collectors.toList());
    }

    @Override
    public List<Integer> toList(IntStream intStream) {
        return intStream
                .boxed()
                .collect(Collectors.toList());
    }

    @Override
    public IntStream createIntStream(int start, int end) {
        return IntStream.rangeClosed(start, end);
    }

    @Override
    public DoubleStream squareRootIntStream(IntStream intStream) {
        return intStream.mapToDouble(d -> (int) Math.sqrt(d));
    }

    @Override
    public IntStream getOdd(IntStream intStream) {
        return intStream.filter(i -> i % 2 == 1);
    }

    @Override
    public Consumer<String> getLambdaPrinter(String prefix, String suffix) {
        return message -> System.out.println(prefix + message + suffix);
    }

    @Override
    public void printMessages(String[] messages, Consumer<String> printer) {
        Stream
                .of(messages)
                .forEach(printer);
    }

    @Override
    public void printOdd(IntStream intStream, Consumer<String> printer) {
        getOdd(intStream)
                .mapToObj(Integer::toString)
                .forEach(printer);
    }

    @Override
    public Stream<Integer> flatNestedInt(Stream<List<Integer>> ints) {
        return ints.flatMap(l -> l
                .stream()
                .map(i -> i*i));
    }
}
