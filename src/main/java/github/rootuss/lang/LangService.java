package github.rootuss.lang;

import java.util.List;

import static java.util.stream.Collectors.toList;

class LangService {
    private LangRepository repository;

    LangService() {
        this(new LangRepository());
    }

    LangService(LangRepository repository) {
        this.repository = repository;
    }

    //zwraca listę z parami id oraz code
    List<LangDTO> findAll() {
        return repository
                .findAll()
                .stream()
                // nastepnie mapujemy na obiekt typu LangDTO, aby później zrobić JSON'a
                .map(LangDTO::new)
                .collect(toList());
    }

}
