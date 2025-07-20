import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EstadoCajaComponent } from './estado-caja.component';

describe('EstadoCajaComponent', () => {
  let component: EstadoCajaComponent;
  let fixture: ComponentFixture<EstadoCajaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EstadoCajaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EstadoCajaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
