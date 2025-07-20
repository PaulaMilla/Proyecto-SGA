import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListadoMovimientoComponent } from './listado-movimiento.component';

describe('ListadoMovimientoComponent', () => {
  let component: ListadoMovimientoComponent;
  let fixture: ComponentFixture<ListadoMovimientoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ListadoMovimientoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListadoMovimientoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
