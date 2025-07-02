package miniproject.domain;

import miniproject.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//<<< PoEAA / Repository
@RepositoryRestResource(collectionResourceRel = "openAis", path = "openAis")
public interface OpenAiRepository
    extends PagingAndSortingRepository<OpenAi, Long> {}
