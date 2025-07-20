import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SeleccionarCajaComponent } from './seleccionar-caja.component';

describe('SeleccionarCajaComponent', () => {
  let component: SeleccionarCajaComponent;
  let fixture: ComponentFixture<SeleccionarCajaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SeleccionarCajaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SeleccionarCajaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
