package be.vdab.groenetenen.services;

import be.vdab.groenetenen.domain.Filiaal;
import be.vdab.groenetenen.exceptions.FIliaalHeeftNogWerknemersException;
import be.vdab.groenetenen.repositories.FiliaalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
public class DefaultFiliaalService implements FiliaalService
{
    private final FiliaalRepository filiaalRepository;
    DefaultFiliaalService(FiliaalRepository filiaalRepository) {
        this.filiaalRepository = filiaalRepository;
    }

    @Override
    public List<Filiaal> findByPostcode(int van, int tot) {
        return filiaalRepository.findByAdresPostcodeBetweenOrderByAdresPostcode(van, tot);
    }

    @Override
    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
    public void delete(Filiaal filiaal) {
        if (!filiaal.getWerknemers().isEmpty())
        {
            throw new FIliaalHeeftNogWerknemersException();
        }
        filiaalRepository.delete(filiaal);
    }

    @Override
    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
    public void create(Filiaal filiaal) {
        filiaalRepository.save(filiaal);

    }

    @Override
    public void update(Filiaal filiaal) {
        filiaalRepository.save(filiaal);
    }

    @Override
    public List<Filiaal> findAll() {
        return  filiaalRepository.findAll();
    }

    @Override
    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
    public void afschrijven(long id) {
        filiaalRepository.findById(id).ifPresent(filiaal -> filiaal.afschrijven());
    }
}
