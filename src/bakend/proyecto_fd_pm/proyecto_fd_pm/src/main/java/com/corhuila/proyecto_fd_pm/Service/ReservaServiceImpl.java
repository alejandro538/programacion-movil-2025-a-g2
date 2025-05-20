package com.corhuila.proyecto_fd_pm.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.corhuila.proyecto_fd_pm.Dto.ReservaDTO;
import com.corhuila.proyecto_fd_pm.Repository.IMaterialRepository;
import com.corhuila.proyecto_fd_pm.Repository.IReservaRepository;
import com.corhuila.proyecto_fd_pm.Repository.IUsuarioRepository;
import com.corhuila.proyecto_fd_pm.models.Material;
import com.corhuila.proyecto_fd_pm.models.Reserva;
import com.corhuila.proyecto_fd_pm.models.Usuario;

@Service
public class ReservaServiceImpl implements IReservaService {

    @Autowired
    private IReservaRepository ireservarepository;

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private IMaterialRepository materialRepository;

    // Obtiene todas las reservas y las convierte a DTO para la capa superior
    @Override
    public List<ReservaDTO> getAllReservas() {
        List<Reserva> reservas = ireservarepository.findAll();
        return reservas.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Crea una nueva reserva validando que el usuario tenga rol USER
    @Override
    public ReservaDTO createReserva(ReservaDTO reservaDTO) {
        Usuario usuario = usuarioRepository.findById(reservaDTO.getUsuarioId())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        if (!usuario.getRol().name().equalsIgnoreCase("USER")) {
            throw new SecurityException("Solo los usuarios pueden hacer reservas");
        }

        Material material = materialRepository.findById(reservaDTO.getMaterialId())
                .orElseThrow(() -> new IllegalArgumentException("Material no encontrado"));

        Reserva reserva = new Reserva(
                reservaDTO.getFechaInicio(),
                reservaDTO.getFechaFin(),
                reservaDTO.getEstado(),
                usuario,
                material
        );

        Reserva nuevaReserva = ireservarepository.save(reserva);
        return convertToDTO(nuevaReserva);
    }

    // Actualiza una reserva validando permisos y campos permitidos
    @Override
    public ReservaDTO updateReserva(Long reservaId, Long usuarioId, ReservaDTO reservaDTO) {
        Reserva reservaExistente = ireservarepository.findById(reservaId)
                .orElseThrow(() -> new IllegalArgumentException("Reserva no encontrada con ID: " + reservaId));

        if (!reservaExistente.getUsuario().getId().equals(usuarioId)) {
            throw new SecurityException("No tienes permiso para actualizar esta reserva.");
        }

        reservaExistente.setFechaInicio(reservaDTO.getFechaInicio());
        reservaExistente.setFechaFin(reservaDTO.getFechaFin());
        reservaExistente.setEstado(reservaDTO.getEstado());

        if (reservaDTO.getMaterialId() != null) {
            Material material = materialRepository.findById(reservaDTO.getMaterialId())
                    .orElseThrow(() -> new IllegalArgumentException("Material no encontrado"));
            reservaExistente.setMaterial(material);
        }

        Reserva reservaActualizada = ireservarepository.save(reservaExistente);
        return convertToDTO(reservaActualizada);
    }

    // Elimina una reserva validando permisos
    @Override
    public void deleteReserva(Long id, Long usuarioId) {
        Reserva reserva = ireservarepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reserva no encontrada con ID: " + id));

        if (!reserva.getUsuario().getId().equals(usuarioId)) {
            throw new SecurityException("No tienes permiso para eliminar esta reserva.");
        }

        ireservarepository.deleteById(id);
    }

    // Obtiene reservas filtrando por usuario
    @Override
    public List<ReservaDTO> getReservasByUsuarioId(Long usuarioId) {
        List<Reserva> reservas = ireservarepository.findByUsuarioId(usuarioId);
        return reservas.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Obtiene reservas filtrando por estado (en memoria)
    @Override
    public List<ReservaDTO> getReservasByEstado(String estado) {
        List<Reserva> reservas = ireservarepository.findAll().stream()
                .filter(r -> r.getEstado().toString().equalsIgnoreCase(estado))
                .collect(Collectors.toList());
        return reservas.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Conversión de DTO a entidad Reserva
    @Override
    public Reserva convertToEntity(ReservaDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        Material material = materialRepository.findById(dto.getMaterialId())
                .orElseThrow(() -> new IllegalArgumentException("Material no encontrado"));

        return new Reserva(
                dto.getFechaInicio(),
                dto.getFechaFin(),
                dto.getEstado(),
                usuario,
                material
        );
    }

    // Conversión de entidad Reserva a DTO
    @Override
    public ReservaDTO convertToDTO(Reserva reserva) {
        return new ReservaDTO(
                reserva.getId(),
                reserva.getFechaInicio(),
                reserva.getFechaFin(),
                reserva.getEstado(),
                reserva.getUsuario().getId(),
                reserva.getUsuario().getNombre(),
                reserva.getMaterial().getId(),
                reserva.getMaterial().getNombre()
        );
    }

    // Obtiene una reserva por su ID
    @Override
    public Reserva getReservaById(Long id) {
        return ireservarepository.findById(id).orElse(null);
    }

    // Guarda una reserva (creación o actualización)
    @Override
    public Reserva saveReserva(Reserva reserva) {
        return ireservarepository.save(reserva);
    }
}
